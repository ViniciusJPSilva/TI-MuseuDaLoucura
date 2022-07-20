package ti.vjps.museu.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ti.vjps.museu.dao.UsuarioDAO;
import ti.vjps.museu.modelo.Usuario;

@SessionScoped //Trata informações de Login.
@ManagedBean
public class LoginBean {
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String efetuaLogin() {
		UsuarioDAO dao = new UsuarioDAO();
		boolean loginValido = dao.existe(usuario);
		
		if(loginValido)
			return "admin?faces-redirect=true";
		else {
			usuario = new Usuario();
			return "login?faces-redirect=true";
		}
	}
	
	public String logout() {
		usuario = new Usuario();
		return "login?faces-redirect=true";
	}
	
	public boolean isLogado() {
		return usuario.getLogin() != null;
	}

}
