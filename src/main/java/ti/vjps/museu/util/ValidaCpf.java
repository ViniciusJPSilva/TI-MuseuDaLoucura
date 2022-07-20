package ti.vjps.museu.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validaCpf")
public class ValidaCpf implements Validator {

	public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String cpf = value.toString();
		
		int soma = 0;
	    for(int i=0; i < cpf.length(); i++)
	    	if(cpf.charAt(i) != '.' && cpf.charAt(i) != '-')
	            soma += Integer.parseInt(String.valueOf(cpf.charAt(i)));

	        switch (soma){
	            case 10:
	            case 11:
	            case 12:
	            case 21:
	            case 22:
	            case 23:
	            case 32:
	            case 33:
	            case 34:
	            case 43:
	            case 44:
	            case 45:
	            case 54:
	            case 55:
	            case 56:
	            case 65:
	            case 66:
	            case 67:
	            case 76:
	            case 77:
	            case 78:
	            case 87:
	            case 88:
	                return;
	            default:
	            	throw new ValidatorException(new FacesMessage("CPF inválido"));
	        }
	}
}
