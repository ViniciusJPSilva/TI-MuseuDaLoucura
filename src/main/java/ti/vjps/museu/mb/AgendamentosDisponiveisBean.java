package ti.vjps.museu.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;

import ti.vjps.museu.dao.DAO;
import ti.vjps.museu.modelo.Agendamento;

@SuppressWarnings("serial")
@ManagedBean
public class AgendamentosDisponiveisBean  implements Serializable {
	
	private final int NUMERO_VAGAS_HORARIO = 10;

	public List<Integer> horariosDisponiveis(Calendar data){
		List<Integer> horariosDisponiveis = new ArrayList<>();
				
//		(9, 10, 11, 12, 13, 14, 15, 16, 17, 18)
		for(int hora = 9; hora <= 18; hora++) {
			int vagas = vagasDisponiveisHorario(data, hora);
			if(vagas > 0)
				horariosDisponiveis.add(hora);
		}
		
		return horariosDisponiveis;
	} // horariosDisponiveis()
	
	public List<Integer> vagasDisponiveis(Calendar data, int horario){
		List<Integer> vagas = new ArrayList<>();
		int vagasHorario = vagasDisponiveisHorario(data, horario);
		for(int numVagas = 1; numVagas <= vagasHorario; numVagas++)
			vagas.add(numVagas);
		
		return vagas;
	}
	
	public int vagasDisponiveisHorario(Calendar data, int horario) {
		int vagas = NUMERO_VAGAS_HORARIO;
		
		for(Agendamento agendamento : new DAO<Agendamento>(Agendamento.class).listaTodos()) {
			
			String dataWeb = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());
			String dataBd = new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData().getTime());
			
			if(dataWeb.equals(dataBd) && agendamento.getHorario() == horario)
				vagas -= agendamento.getGrupoPessoas().size();
		}
		
		return vagas;
	} // vagasDisponiveis()
	
	public boolean emailJaAgendado(String email, Calendar data, int horario) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String dataWeb = sdf.format(data.getTime());
		
		for(Agendamento agendamento : new DAO<Agendamento>(Agendamento.class).listaTodos()) {
			String dataBd = sdf.format(agendamento.getData().getTime());
			if(agendamento.getEmail().equalsIgnoreCase(email) && dataWeb.equals(dataBd) && agendamento.getHorario() == horario)
				return true;
		}
		
		return false;
	}
	
} // class AgendamentosDisponiveisBean
