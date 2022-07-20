package ti.vjps.museu.mb;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ti.vjps.museu.dao.DAO;
import ti.vjps.museu.modelo.Agendamento;
import ti.vjps.museu.modelo.Pessoa;
import ti.vjps.museu.util.EmailController;
import ti.vjps.museu.util.HashUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AgendamentoBean implements Serializable {

	private Agendamento agendamento = new Agendamento();
	private Pessoa pessoa = new Pessoa();
	private int vagas = 0;
	
	public void gravar() {
		int val = validarAgendamento();
		if(val == 1) {
			DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);
			
			agendamento.setNumPessoas(agendamento.getGrupoPessoas().size());
			agendamento.setHash(HashUtils.getHashMd5(String.format("%s %s", agendamento.getEmail(), agendamento.getData().getTimeInMillis())));
			
			dao.adiciona(agendamento);
			
			StringBuilder mensagem = new StringBuilder();
			mensagem.append(String.format("<h3>Seu agendamento foi confirmado!</h3><br/>Data: %s<br/>Horário: %d<br/><br/>Quantidade de pessoas: %d<br/><br/>Pessoas do Grupo:<br/>", new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData().getTime()), agendamento.getHorario(), agendamento.getGrupoPessoas().size()));
		
			for(Pessoa p : agendamento.getGrupoPessoas())
				mensagem.append(String.format("%s - %s<br/>", p.getCpf(), p.getNome()));
			
			mensagem.append(String.format("<br/>Seu código de agendamento: %s<br/>", agendamento.getHash()));
			
			
			new EmailController(mensagem.toString(), "Museu da Loucura - Confirmação de Agendamento", agendamento.getEmail());
			
			agendamento = new Agendamento();
			pessoa = new Pessoa();
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/MuseuDaLoucura-ViniciusJosePiresSilva/agendado.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(val == -0)
			FacesContext.getCurrentInstance().addMessage("mensagensErros", new FacesMessage("Preencha todos os dados necessários!"));
	}
	
	public void guardaPessoa() {
		if(!agendamento.getGrupoPessoas().contains(pessoa))
			if(vagas > agendamento.getGrupoPessoas().size())
				agendamento.getGrupoPessoas().add(pessoa);
			else
				FacesContext.getCurrentInstance().addMessage("mensagensErros", new FacesMessage("O limite de Pessoas foi alcançado"));
		else
			FacesContext.getCurrentInstance().addMessage("mensagensErros", new FacesMessage("Pessoa já adicionada!"));
		
		pessoa = new Pessoa();
	}
	
	public void buscarCancelamento() {
		DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);
		
		for(Agendamento a : dao.listaTodos()) 
			if(agendamento.getEmail().equals(a.getEmail()) && agendamento.getHash().equalsIgnoreCase(a.getHash())) {
				agendamento = a;
				return;
			}
		
		agendamento = new Agendamento();
	}
	
	public void cancelar() {
		DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);
		dao.remove(agendamento);
		
		StringBuilder mensagem = new StringBuilder();
		mensagem.append(String.format("<h3>Seu agendamento foi Cancelado!</h3><br/>Data: %s<br/>Horário: %d<br/>", new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData().getTime()), agendamento.getHorario()));
		mensagem.append(String.format("<br/>Seu código de agendamento: %s<br/>", agendamento.getHash()));
		
		new EmailController(mensagem.toString(), "Museu da Loucura - Agendamento Cancelado", agendamento.getEmail());
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/MuseuDaLoucura-ViniciusJosePiresSilva/cancelado.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeBD(Pessoa pessoa) {
		DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);
		
		remove(pessoa);
		
		dao.atualiza(agendamento);
		
		StringBuilder mensagem = new StringBuilder();
		mensagem.append(String.format("<h3>Seu agendamento foi Alterado!</h3><br/>Data: %s<br/>Horário: %d<br/><br/>Quantidade de pessoas: %d<br/><br/>Pessoas do Grupo:<br/>", new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData().getTime()), agendamento.getHorario(), agendamento.getGrupoPessoas().size()));
	
		for(Pessoa p : agendamento.getGrupoPessoas())
			mensagem.append(String.format("%s - %s<br/>", p.getCpf(), p.getNome()));
		
		mensagem.append(String.format("<br/>Seu código de agendamento: %s<br/>", agendamento.getHash()));
		
		new EmailController(mensagem.toString(), "Museu da Loucura - Agendamento Alterado", agendamento.getEmail());
		
		if(agendamento.getGrupoPessoas().size() == 0)
			cancelar();
	}
	
	public void remove (Pessoa pessoa) {
		agendamento.getGrupoPessoas().remove(pessoa);
		pessoa = new Pessoa();
	}
	
	public void removeTodasPessoas() {
		agendamento.getGrupoPessoas().clear();
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		if(vagas < agendamento.getGrupoPessoas().size()) {
			agendamento.setGrupoPessoas(new ArrayList<Pessoa>());
			FacesContext.getCurrentInstance().addMessage("mensagensErros", new FacesMessage("A lista de Pessoas foi apagada!"));
		}
		
		this.vagas = vagas;
	}
	
	private int validarAgendamento() {
		if(agendamento.getEmail() == null)
			return 0;
		
		if(agendamento.getEmail().isBlank())
			return 0;
		
		if(new AgendamentosDisponiveisBean().emailJaAgendado(agendamento.getEmail(), agendamento.getData(), agendamento.getHorario())) {
			FacesContext.getCurrentInstance().addMessage("mensagensErros", new FacesMessage("Há um agendamento em aberto para este email no horário selecionado!"));
			return -1;
		}
			
		if(agendamento.getGrupoPessoas().size() < 1)
			return 0;
		
		return 1;
		
	}
	
}// class PacienteBean
