package ti.vjps.museu.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Usuario {

	@Id
	@SequenceGenerator(
			name="usuario_id", 
			sequenceName="usuario_seq", 
			allocationSize=1)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, 
			generator="usuario_id")
	private Long id;
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}
 
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
} // class Usuario 
