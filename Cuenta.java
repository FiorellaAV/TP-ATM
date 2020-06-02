import java.util.ArrayList;
import java.util.Collections;

public class Cuenta {
	
	protected String tipoToString;
	protected double saldo;
	protected String alias;
	protected Cliente cliente;
	protected ArrayList<Movimiento> historialDeMovimientos;
	
	public Cuenta(String alias, double saldo, ArrayList<Movimiento> historialDeMovimientos){
		this.saldo = saldo;
		this.alias = alias;			
		Collections.reverse(historialDeMovimientos);
		this.historialDeMovimientos = historialDeMovimientos;
	}
	
	public void incrementarSaldo(double suma ) {
		this.saldo +=suma;
	}
	
	public void reducirSaldo(double resta) throws ErrorDeTransaccion {
		if(this.saldo >= resta) {
				this.saldo -= resta;
		}else { 
			throw new ErrorDeTransaccion("Saldo insuficiente");
		}
	}
	
	public ArrayList<String> getUltimosMovimientos() {
		ArrayList<String> ultimosMovimientos = new ArrayList<String>();
		for(int i = 0; i<historialDeMovimientos.size() && i<10 ; i++) {
			ultimosMovimientos.add(historialDeMovimientos.get(i).toString());
		}
		
		return ultimosMovimientos;
	}
	
	public String toString() {
		return this.alias+","+this.saldo;
	}

	public double getSaldo() {
		return saldo;
	}

	public String getAlias() {
		return alias;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public ArrayList<Movimiento> getHistorialDeMovimientos() {
		return historialDeMovimientos;
	}
	public void setMovimiento(Movimiento movimiento) {
		historialDeMovimientos.add(movimiento);
	}
	
}