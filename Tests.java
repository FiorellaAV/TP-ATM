import java.io.IOException;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

public class Tests {

	@Test
	public void pruebaGetAliasDeCuentaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta.getAlias(), mf.getCuenta(0).getAlias());		
	}
	@Test
	public void pruebaGetSaldoDeCuentaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta.getSaldo(), mf.getCuenta(0).getSaldo(), 0);
	}
	@Test
	public void pruebaGetPinDeCuentaGuardado() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta.getCliente(), mf.getCuenta(0).getCliente());
	}
	@Test
	public void pruebaGetAliasDeCuenta2Guardado() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta2.getAlias(), mf.getCuenta(1).getAlias());		
	}
	@Test
	public void pruebaGetSaldoDeCuenta2Guardado() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta2.getSaldo(), mf.getCuenta(1).getSaldo(), 0);
	}
	@Test
	public void pruebaGetPinDeCuenta2Guardado() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta2.getCliente(), mf.getCuenta(1).getCliente());
	}
	@Test
	public void depositarEnCuentaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		cuenta.incrementarSaldo(1000); 
		mf.getCuenta(0).incrementarSaldo(1000);

		Assert.assertEquals(cuenta.getSaldo(), mf.getCuenta(0).getSaldo(),0);	
	}
	@Test
	public void retirarEnCuentaGuardada() throws IOException, ErrorDeTransaccion{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		cuenta.reducirSaldo(1000);
		mf.getCuenta(0).reducirSaldo(1000);
		
		Assert.assertEquals(cuenta.getSaldo(),mf.getCuenta(0).getSaldo(),0);
	}
	@Test
	public void pruebaGetNumeroDeTarjetaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(tarjeta.getNumero(), mf.getTarjeta(0).getNumero());
	}
	@Test
	public void pruebaGetCuitDeTarjetaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(tarjeta.getCuit(), mf.getTarjeta(0).getCuit());
	}
	@Test
	public void pruebaGetPinDeTarjetaGuardada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(tarjeta.getPin(), mf.getTarjeta(0).getPin());
	}
	@Test
	public void pruebaGetNumeroDeTarjetaEquivocada() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertNotEquals(tarjeta2.getNumero(), mf.getTarjeta(0).getNumero());
	}
	@Test
	public void pruebaGetAliasDeCuenta3() throws IOException{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		Assert.assertEquals(cuenta3.getAlias(), mf.getCuenta(4).getAlias());
	}
	@Test(expected = Excepciones.class)
	public void pruebaRetirarEfectivoDeCuentaConSaldoNegativo() throws IOException, Excepciones{
		Cajero mf = new Cajero();
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.03, null);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.90,25000.00, null);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, null);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		mf.retirar(cuenta2, 24000);
		
		Assert.assertEquals(cuenta2.getSaldo(),mf.getCuenta(1).getSaldo(),0);
		
	}
	@Test
	public void comprarDolares(){
		Cajero cajero = new Cajero();
		ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
			
		Movimiento m1=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m2=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m3=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m4=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m5=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m6=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m7=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m8=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m9=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		
		movimientos.add(m1);movimientos.add(m2);movimientos.add(m3);movimientos.add(m4);movimientos.add(m5);movimientos.add(m6);
		movimientos.add(m7);movimientos.add(m8);movimientos.add(m9);
		
		
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.00 ,movimientos);
		CuentaCorriente cuenta2 = new CuentaCorriente ("sol.monte.valle",-1021.00,25000.00, movimientos);
		CajaAhorroDolares cuenta3 = new CajaAhorroDolares("lobo.luna.pasto", 200.00, movimientos);
		Tarjeta tarjeta = new Tarjeta("12345678","1234","27102551236");
		Tarjeta tarjeta2 = new Tarjeta("23456789","2345","27102551236");
		
		
		
		cajero.compraDeDolares(cuenta2, cuenta3, 50);
		double saldo = cuenta2.getSaldo();
		double saldoDolar = cuenta3.getSaldo();
		Assert.assertEquals(-1021.00-(50*71.17*1.30), saldo, 0);
		Assert.assertEquals(250, saldoDolar, 0);
	}
	
	@Test
	public void pruebaGetUltimosMovimientos(){
		Cajero cajero = new Cajero();
		ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
			
		Movimiento m1=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m2=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m3=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m4=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m5=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m6=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m7=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m8=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		Movimiento m9=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 50.00 );
		
		movimientos.add(m1);movimientos.add(m2);movimientos.add(m3);movimientos.add(m4);movimientos.add(m5);movimientos.add(m6);
		movimientos.add(m7);movimientos.add(m8);movimientos.add(m9);
		
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.00 ,movimientos);
	
		ArrayList<String> comparacion = cuenta.getUltimosMovimientos();
		for(String string : comparacion) {
			Assert.assertEquals("2020-05-30, Peron,isla.pez.arbol, 50.00",  string);
		}
	}
	
	@Test
	public void imprimir(){
		Cajero cajero = new Cajero();
		ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
			
		Movimiento m1=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 1.00 );
		Movimiento m2=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 2.00 );
		Movimiento m3=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 3.00 );
		Movimiento m4=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 4.00 );
		Movimiento m5=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 5.00 );
		Movimiento m6=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 6.00 );
		Movimiento m7=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 7.00 );
		Movimiento m8=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 8.00 );
		Movimiento m9=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 9.00 );
		Movimiento m10=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 10.00 );
		Movimiento m11=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 11.00 );
		Movimiento m12=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 12.00 );
		Movimiento m13=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 13.00 );
		Movimiento m14=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 14.00 );
		Movimiento m15=new Movimiento("2020-05-30", "Peron","isla.pez.arbol", 15.00 );
		
		movimientos.add(m1);movimientos.add(m2);movimientos.add(m3);movimientos.add(m4);movimientos.add(m5);movimientos.add(m6);
		movimientos.add(m7);movimientos.add(m8);movimientos.add(m9);
		
		CajaAhorroPesos cuenta = new CajaAhorroPesos("isla.pez.arbol", 12000.00 ,movimientos);
	
		try {
			cajero.consultarUltimosMovimientos(cuenta);
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
