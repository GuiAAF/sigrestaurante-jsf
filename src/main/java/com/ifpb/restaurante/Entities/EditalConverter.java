package com.ifpb.restaurante.Entities;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.ifpb.restaurante.service.EditalService;
import com.ifpb.restaurante.service.ServiceDacaException;




@FacesConverter(value = "editalConverter", forClass = Edital.class)
public class EditalConverter implements Converter {

	// XXX: Injeção de dependência possível graças ao Omnifaces, pois 
	// CDI 1.1 e conversores do JSF 2.2 não conversam.
	// Referências: http://showcase.omnifaces.org/cdi/FacesConverter
	// http://showcase.omnifaces.org/cdi/FacesValidator
	@Inject
	private EditalService editais;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {
			int id = Integer.parseInt(value);
			return editais.getByID(id);
		} catch (NumberFormatException | ServiceDacaException e) {
			String msgErroStr = String.format(
					"Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
					value);
			FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (!(value instanceof Edital)) {
			return null;
		}
		
		Integer id = ((Edital) value).getId();
		return (id != null) ? id.toString() : null;
	}

}
