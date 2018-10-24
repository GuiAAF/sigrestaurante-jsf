package com.ifpb.restaurante.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("stringValidator")
public class ValidarString  implements Validator{
	
	static final String[] num = {"0","1","2","3","4","5","6","7","8","9"};
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String valor = String.valueOf(value);
		String str = new String(""); 
		for(int i = 0; i < valor.length(); i++){
			str = Character.toString(valor.charAt(i));
			for(int j = 0; j < num.length; j++){
				if(str.equals(num[j])) {
					FacesMessage msg = new FacesMessage("O valor informado contem números!!");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);	
				}
			}
		}
		
	}

}
