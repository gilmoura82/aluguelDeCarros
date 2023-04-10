package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		
		// Cálculo  
		
		double minutes = Duration.between(carRental.getInicio(), carRental.getFim()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if (hours <= 12.0) {
			// Até 12 horas, cálculo será por hora
			basicPayment = pricePerHour * Math.ceil(hours);
		}
		else {
			// Após 12 horas será cálculo por dia
			basicPayment = pricePerDay * Math.ceil(hours / 24.0);
		}
		
		// Cálculo do Imposto
		
		double tax = taxService.tax(basicPayment);
				
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
		
		
		
	}
	
	
	
	

}
