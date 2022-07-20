package ti.vjps.museu.modelo;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Pessoa{
	
	@Transient
	public final String TIPOS_INGRESSO[] = {"Inteiro", "Meia Entrada", "Isento", "Morador Cadastrado", "Condutores Credenciados"};

	@Id
	@SequenceGenerator( // Cria a sequência pessoa_seq
		name="pessoa_id",
		sequenceName="pessoa_seq",
		allocationSize=1)
	@GeneratedValue( // Define que o atributo id terá seu valor gerado pelo proximo valor da sequencia pessoa_seq
		strategy=GenerationType.SEQUENCE, 
		generator="pessoa_id")
	private Long id;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String tipoIngresso;
	
	private boolean compareceu = false;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipoIngresso() {
		return tipoIngresso;
	}
	
	public void setTipoIngresso(String tipoIngresso) {
		this.tipoIngresso = tipoIngresso;
	}
	
	public boolean getCompareceu() {
		return compareceu;
	}

	public void setCompareceu(boolean compareceu) {
		this.compareceu = compareceu;
	}

	public List<String> getTiposIngresso() {
		return Arrays.asList(TIPOS_INGRESSO);	
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", tipoIngresso=" + tipoIngresso + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pessoa)
			return (cpf.equalsIgnoreCase(((Pessoa)obj).getCpf()));
		else
			return false;
	}
	
} // class Pessoa
