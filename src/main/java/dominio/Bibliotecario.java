package dominio;

import java.util.Date;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn, String nombreUsuario) {
		Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
		Prestamo prestamo = new Prestamo(new Date(),libro,null,nombreUsuario);
		repositorioPrestamo.agregar(prestamo);
		//throw new UnsupportedOperationException("Método pendiente por implementar");

	}

	public boolean esPrestado(String isbn) {	
		Prestamo pre =repositorioPrestamo.obtener(isbn);
		return repositorioPrestamo.obtener(isbn)!=null ? true : false;		
	}
	public boolean esPalindrome(String isbn){
		
		return false;
	}

}
