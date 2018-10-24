package com.ifpb.restaurante.bean;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.service.EditalService;
import com.ifpb.restaurante.service.ServiceDacaException;

@Named
@RequestScoped
public class IndexAdm extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2132897271787815405L;
	
	@Inject
	private EditalService editalService;
	
	List<Edital> editais;

	
	public void atualizarBd() {
		try {
			
			editais = editalService.getAll();
			Date dataHoje = new Date();
				for (Edital ed : editais) {
					if(ed.getDataTermino().before(dataHoje) || ed.getDataInicio().after(dataHoje)) {
						ed.setVigente(false);
					}
					editalService.update(ed);
				}
		} catch (ServiceDacaException e) {
			e.printStackTrace();
		}
	}
}
