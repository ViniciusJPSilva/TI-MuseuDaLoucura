package ti.vjps.museu.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ImagensViewBean {

	private List<String> imagens;

	@PostConstruct
	public void init() {
		imagens = new ArrayList<String>();
		for (int i = 1; i <= 8; i++) {
			imagens.add("atrac" + i + ".jpg");
		}
	}

	public List<String> getImagens() {
		return imagens;
	}
}
