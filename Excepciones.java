@SuppressWarnings("serial")
public class Excepciones extends Exception{
	
	private String error;
	
	public Excepciones (String error){
		this.error = error;
	}
	
	public String obtenerError() {
		return error;
	}
}
