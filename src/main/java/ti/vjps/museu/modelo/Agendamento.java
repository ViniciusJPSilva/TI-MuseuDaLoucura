package ti.vjps.museu.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Agendamento {
	
	@Id
	@SequenceGenerator( // Cria a sequência agendamento_seq
		name="agendamento_id",
		sequenceName="agendamento_seq",
		allocationSize=1)
	@GeneratedValue( // Define que o atributo id terá seu valor gerado pelo proximo valor da sequencia agendamento_seq
		strategy=GenerationType.SEQUENCE, 
		generator="agendamento_id")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Calendar data = Calendar.getInstance();
	
	private int horario;
	private int numPessoas;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String hash;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Pessoa> grupoPessoas = new ArrayList<Pessoa>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public int getHorario() {
		return horario;
	}

	public void setHorario(int horario) {
		this.horario = horario;
	}

	public int getNumPessoas() {
		return numPessoas;
	}

	public void setNumPessoas(int numPessoas) {
		this.numPessoas = numPessoas;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<Pessoa> getGrupoPessoas() {
		return grupoPessoas;
	}

	public void setGrupoPessoas(List<Pessoa> grupoPessoas) {
		this.grupoPessoas = grupoPessoas;
	}

	@Override
	public String toString() {
		return "Agendamento [id=" + id + ", data=" + data + ", horario=" + horario + ", email=" + email + "]";
	}
	
} // class Agendamento
