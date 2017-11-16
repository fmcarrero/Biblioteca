package dominio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String LOS_LIBROS_PALINDROMOS_SOLO_SE_PUEDE_UTILIZAR_EN_LA_BIBLIOTECA="los libros palíndromos solo se pueden utilizar en la biblioteca";
	public final int Cantidad =30;
	public final int MaximoDias =14;
	
	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn, String nombreUsuario) {
		
		Libro existe = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		if(existe!=null){
			throw new PrestamoException(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		
		String numberOnly =getNumbers(isbn);
		if(esPalindrome(numberOnly)){
			throw new UnsupportedOperationException(LOS_LIBROS_PALINDROMOS_SOLO_SE_PUEDE_UTILIZAR_EN_LA_BIBLIOTECA);
		}
		long number = Long.valueOf(numberOnly);
		long suma =sumDigits(number);
		Date fechaEntregaMaxima =null;
		if(suma>Cantidad){
			fechaEntregaMaxima=CalcularFechaEntregaMaxima(new Date());
		}
		Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
		Prestamo prestamo = new Prestamo(new Date(),libro,fechaEntregaMaxima,nombreUsuario);
		repositorioPrestamo.agregar(prestamo);
	}
	
	
	public Date CalcularFechaEntregaMaxima(Date fecha){
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int businessDayCounter=0;	
		int dayOfWeek=0;
		while (businessDayCounter < MaximoDias) {
		    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		    if (dayOfWeek != Calendar.SUNDAY) {
		        businessDayCounter++;		       
		    }
		    cal.add(Calendar.DAY_OF_YEAR, 1);		    
		}
		//valida si el dia es domingo de ser asi agrega un dia mas
		if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.SUNDAY){
			 cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return cal.getTime();		
	}
	public boolean esPrestado(String isbn) {			
		return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) !=null ? true : false;		
	}
	public boolean esPalindrome(String numberOnly){
		return numberOnly.equals(new StringBuilder(numberOnly).reverse().toString());		
	}
	public String getNumbers(String isbn){
		return  isbn.replaceAll("[^0-9]", "");		
	}	
	
	public long sumDigits(long i) {
	    return i == 0 ? 0 : i % 10 + sumDigits(i / 10);
	}

}
