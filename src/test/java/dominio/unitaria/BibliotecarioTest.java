package dominio.unitaria;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.Prestamo;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private final String nombreUsuario = "Franklin Carrero";
	@Test
	public void esPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertTrue(esPrestado);
	}
	
	@Test
	public void libroNoPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertFalse(esPrestado);
	}
	@Test
	public void Prestar(){
		//arrange
		
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("T878B85zZ").conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA);
		Libro libro = libroTestDataBuilder.build(); 
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);			
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);		
		Date fechaEntregaMaxima=bibliotecario.CalcularFechaEntregaMaxima(new Date());
		Prestamo prestamo = new Prestamo(new Date(),libro,fechaEntregaMaxima,nombreUsuario);	
		repositorioPrestamo.agregar(prestamo);		
		
		//act
		bibliotecario.prestar(libro.getIsbn(), nombreUsuario);
		
		//assert
		verify(repositorioPrestamo).agregar(prestamo);		
	}
}
