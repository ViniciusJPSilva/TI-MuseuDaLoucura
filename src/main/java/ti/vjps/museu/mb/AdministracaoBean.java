package ti.vjps.museu.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ti.vjps.museu.dao.DAO;
import ti.vjps.museu.modelo.Agendamento;
import ti.vjps.museu.modelo.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AdministracaoBean implements Serializable {
	
	private Agendamento agendamento = new Agendamento();
	private List<Pessoa> pessoas = new ArrayList<>();
	
	private Calendar dataVisita = Calendar.getInstance();

	public void pessoasAgendadas() {
		DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);

		pessoas = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(Agendamento a : dao.listaTodos()) {
			String dataWeb = sdf.format(agendamento.getData().getTime());
			String dataBd = sdf.format(a.getData().getTime());
			
			if(dataWeb.equals(dataBd) && agendamento.getHorario() == a.getHorario()) 
				for(Pessoa p : a.getGrupoPessoas())
						pessoas.add(p);
		}
		
	}
	
	public int visitasDiarias() {
		DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);
		int numPessoas = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(Agendamento a : dao.listaTodos()) {
			String dataWeb = sdf.format(dataVisita.getTime());
			String dataBd = sdf.format(a.getData().getTime());
			
			if(dataWeb.equals(dataBd)) 
				for(Pessoa p : a.getGrupoPessoas()) {
					System.out.println(p.getCompareceu());
					if(p.getCompareceu())
						numPessoas++;
				}
		}
		
		System.out.println(numPessoas);
		
		return numPessoas;
	}
	
	public List<Integer> horarios(){
		List<Integer> horariosDisponiveis = new ArrayList<>();
				
//		(9, 10, 11, 12, 13, 14, 15, 16, 17, 18)
		for(int hora = 9; hora <= 18; hora++) 
				horariosDisponiveis.add(hora);
		
		return horariosDisponiveis;
	} // horariosDisponiveis()
	
	public void alterarComparecimento(Pessoa pessoa) {
		DAO<Pessoa> dao = new DAO<Pessoa>(Pessoa.class);
		
		pessoa.setCompareceu(!pessoa.getCompareceu());
		dao.atualiza(pessoa);
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Calendar getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Calendar dataVisita) {
		this.dataVisita = dataVisita;
	}
	
	
	
	
	
}
