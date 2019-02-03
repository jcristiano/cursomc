package com.jcmsilv.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jcmsilv.cursomc.domain.Cliente;
import com.jcmsilv.cursomc.domain.enums.TipoCliente;
import com.jcmsilv.cursomc.dto.ClienteNewDTO;
import com.jcmsilv.cursomc.repository.ClienteRepository;
import com.jcmsilv.cursomc.resources.exceptions.FieldMessage;
import com.jcmsilv.cursomc.services.validation.util.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<FieldMessage>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if ( aux != null ) {
			list.add(new FieldMessage("email", "email ja existente"));
		}

		for (FieldMessage msg : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(msg.getMessage()).addPropertyNode(msg.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
