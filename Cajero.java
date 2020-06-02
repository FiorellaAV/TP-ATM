import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class Cajero implements Pantalla{

	private int billetesDe100 = 500;
	private int billetesDe500 = 500;
	private int billetesDe1000 = 500;
	private Scanner teclado;
	private List<Tarjeta> listaDeTarjetas = new ArrayList<Tarjeta>();
	private List<Cuenta> listaDeCuentas = new ArrayList<Cuenta>();
	private List<Movimiento> listaDeMovimientos = new ArrayList<Movimiento>();
	public List<Cliente> listaDeClientes = new ArrayList<Cliente>();
	private Cliente currentCliente;
	private Cuenta currentCuenta;
	private int menuChoice = -1;
	
	//Post: Se inicializa el cajero //
	public static void main(String[] arg) throws IOException, ErrorDeTransaccion, ErrorDeValidacion{
		Cajero cajero = new Cajero();
		cajero.teclado = new Scanner(System.in);
		cajero.cargarDatos();
		if(cajero.ingresarTarjeta()) {
			cajero.elegirCuenta();
			System.out.println("*                BIENVENIDE                *");
			cajero.cargarPantallaPrincipal();
			
		}
	}
	
	//----------------Validación-----------------------------------
	
	//Post: Se ingresa el numero de tarjeta y se comprueba que este en la base de datos//
	private boolean ingresarTarjeta() throws ErrorDeValidacion {
		System.out.println("Bienvenido, por favor introduzca su numero de tarjeta para continuar");
		String numeroDeTarjeta = teclado.nextLine();
		return comparadorDeTarjeta(numeroDeTarjeta);

	}
	/*Post: Se comprueba que el numero de tarjeta este en la lista de tarjetas,
	si se encuentra se le procede a pedir la clave al usuario para proceder
	a la pantalla principal*/
	private boolean comparadorDeTarjeta(String numeroDeTarjeta) throws ErrorDeValidacion{

		String tarjetaNumero = null;
		int	intentos = 0;
		for (int i = 0 ; i<listaDeTarjetas.size(); i++){

			Tarjeta tarjeta = listaDeTarjetas.get(i);
			tarjetaNumero = tarjeta.getNumero();
			String tarjetaPin = tarjeta.getPin();

			if (tarjetaNumero.equals( numeroDeTarjeta)){

				System.out.println("Por favor ingrese su clave ");
				while (intentos <= 3){

					String clave = teclado.nextLine();
					if(clave.equals( tarjetaPin)){	
						String aliasCliente = getAliasCliente(tarjeta.getCuit());
						currentCliente = buscarClientePorAlias(aliasCliente);
						return true;
						
					}else{
						try {
							throw new ErrorDeValidacion("Clave incorrecta");	
						}catch(ErrorDeValidacion e) {
							System.out.println(e.obtenerError());
						}
					}
					intentos++;
				}
				if(intentos == 4){
					try {
						throw new ErrorDeValidacion("Tarjeta bloqueada, comuniquese con su banco emisor ");	
					}catch(ErrorDeValidacion e) {
						System.out.println(e.obtenerError());
					}		
				}return false;
			}else{
                try {
                    throw new ErrorDeValidacion("El numero introducido de tarjeta no se encuentra en nuestra base de datos");
                }catch(ErrorDeValidacion e) {
                    System.out.println(e.obtenerError());
                    System.out.println("Por favor, retire su tarjeta e intente otra vez");
                    ingresarTarjeta();
                }
            }
            return false;
        
		}
		return false;	
	}
	
	//----------------Operaciones bancarias------------------------
	
	//Post: Se realiza un deposito en la cuenta ingresada y con el monto ingresado//
	public void depositar(Cuenta cuenta, double monto) {
		String motivo = "Deposito";
		System.out.println("Desea agregar mas dinero?     (Y/N)");
		if(teclado.nextLine().toLowerCase().equals("y")) {
			depositar(cuenta, monto+getDoubleInput());
		}else {
			cuenta.incrementarSaldo(monto);
			setHistorialDeMovimientos(cuenta, motivo, monto);
			try {
				SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				FileWriter impresor = new FileWriter("tikets\\tiketDeposito.txt");
				impresor.write("******************************************");impresor.append("\n");
				impresor.append("\n");
				impresor.write("Fecha y Hora: "+formatter.format(date));impresor.append("\n");
				impresor.append("\n");
				impresor.write("Cuenta: "+cuenta.getAlias());impresor.append("\n");
				impresor.append("\n");
				impresor.write("Deposito de $"+monto);impresor.append("\n");
				impresor.append("\n");
				impresor.write("Saldo restante: $"+cuenta.getSaldo());impresor.append("\n");
				impresor.write("******************************************");
				impresor.close();
			}catch(IOException e) {
				System.out.print(e.getCause());
			}
			
		}
		
	}
    //Post: Se retira efectivo de la cuenta ingresada y con el monto ingresado//
	public void retirar(Cuenta cuenta, int monto){
		String motivo = "Retiro";
		try {
			if(cuenta.getClass().equals(CajaAhorroDolares.class)) {
				throw new ErrorDeTransaccion("La caja de ahorro en dolares no permite este tipo de operaciones");
			}else {
				if(chequeoDeBilletes(monto)) {
					cuenta.reducirSaldo(monto);
					System.out.println("Confirma el retiro de $"+monto+"     (Y/N)");
					if(teclado.nextLine().toLowerCase().equals("y")) {
						setHistorialDeMovimientos(cuenta, motivo, monto);
						try {
							SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							Date date = new Date(System.currentTimeMillis());
							FileWriter impresor = new FileWriter("tikets\\tiketRetiro.txt");
							impresor.write("******************************************");impresor.append("\n");
							impresor.write("Fecha y Hora: "+formatter.format(date));impresor.append("\n");
							impresor.write("Cuenta: "+cuenta.getAlias());impresor.append("\n");
							impresor.write("Retiro de $"+monto);impresor.append("\n");
							impresor.write("Saldo restante: $"+cuenta.getSaldo());impresor.append("\n");
							impresor.write("******************************************");impresor.append("\n");
							impresor.close();
						}catch(IOException e) {
							System.out.print(e.getCause());
						}
					}else {
						cuenta.incrementarSaldo(monto);
					}
				}else {
					try {
						throw new ErrorDeTransaccion("Billetes Insuficientes");
					}
					catch(ErrorDeTransaccion e) {
						System.out.println(e.obtenerError());
					}
				}
			}
		}catch(ErrorDeTransaccion e) {
			System.out.println(e.obtenerError());
		}
	}
	//Post: Se transfiere dinero de una cuenta a otra
	public void transferencia(Cuenta cuenta, String alias, double monto){
		try {
			Cuenta cuenta2 = buscarCuentaPorAlias(alias);		
			if(cuenta.getClass().equals(CajaAhorroDolares.class)) {
				throw new ErrorDeTransaccion("La caja de ahorro en dolares no permite este tipo de operaciones");
			}else{
				cuenta.reducirSaldo(monto);
				System.out.println("Ingrese el motivo");
				String motivo = teclado.nextLine();
				System.out.println("Confirme la transferencia de $"+monto+" a la cuenta de alias "+cuenta2.getAlias()+"     (Y/N)");
				if(teclado.nextLine().toLowerCase().equals("y")) {
					cuenta2.incrementarSaldo(monto);
					setHistorialDeMovimientos(cuenta, cuenta2, motivo, monto);
					try {
						SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						Date date = new Date(System.currentTimeMillis());
						FileWriter impresor = new FileWriter("tikets\\tiketTransferencia.txt");
						impresor.write("******************************************");impresor.append("\n");
						impresor.write("Fecha y Hora: "+formatter.format(date));impresor.append("\n");
						impresor.write("Cuenta de origen: "+cuenta.getAlias());impresor.append("\n");
						impresor.write("Cuenta destinataria: "+cuenta2.getAlias());impresor.append("\n");
						impresor.write("El monto transferido es de $"+monto);impresor.append("\n");
						impresor.write("Saldo restante: $"+cuenta.getSaldo());impresor.append("\n");
						impresor.write("******************************************");impresor.append("\n");
						impresor.close();
					}catch(IOException e) {
						System.out.print(e.getCause());
					}
					
				}else {
					cuenta.incrementarSaldo(monto);
				}
			}
		}catch(ErrorDeTransaccion e) {
			System.out.println(e.obtenerError());
		}
	}
	//Post: Compra Dolares
	public void compraDeDolares(Cuenta cuenta, Cuenta cuentaDolar, int monto) {
		double precioDolar = 71.17;
		double precioDeCompra = Double.valueOf(monto)*precioDolar*1.30;
		String motivo = "Compra de dolares";
		try {
			if(cuenta.getClass().equals(CajaAhorroDolares.class)) {
				throw new ErrorDeTransaccion("La caja de ahorro en dolares no permite este tipo de operaciones");
			}else {
				cuenta.reducirSaldo(precioDeCompra);
				System.out.println("Confirme la compra de U$D"+monto+"     (Y/N)");
				if(teclado.nextLine().toLowerCase().equals("y")) {
					cuentaDolar.incrementarSaldo(monto);
					setHistorialDeMovimientos(cuenta, motivo, precioDeCompra);
                    setHistorialDeMovimientos(cuentaDolar, motivo, monto);
					try {
						SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						Date date = new Date(System.currentTimeMillis());
						FileWriter impresor = new FileWriter("tikets\\tiketCompraDolar.txt");
						impresor.write("******************************************");impresor.append("\n");
						impresor.write("Fecha y Hora: "+formatter.format(date));impresor.append("\n");
						impresor.write("Cuenta de origen: "+cuenta.getAlias());impresor.append("\n");
						impresor.write("Cuenta destinataria: "+cuentaDolar.getAlias());impresor.append("\n");
						impresor.write("Se compro U$D"+monto+" a $"+precioDeCompra);impresor.append("\n");
						impresor.write("Saldo restante: $"+cuenta.getSaldo());impresor.append("\n");
                        impresor.write("Nuevo saldo de cuenta: $"+cuenta.getSaldo());impresor.append("\n");
						impresor.write("******************************************");impresor.append("\n");
						impresor.close();
					}catch(IOException e) {
						System.out.print(e.getCause());
					}
				}else {
					cuenta.incrementarSaldo(precioDeCompra);
				}
			}
		}catch(ErrorDeTransaccion e) {
			System.out.println(e.obtenerError());
		}
	}
	
	//Post: Se chequea que el cajero cuente con la cantidad necesaria de billetes para relizar la operacion de retiro//
	private boolean chequeoDeBilletes(int monto) throws ErrorDeTransaccion{
		
		if((monto%100!=0&&monto>0))
			throw new ErrorDeTransaccion("El monto debe ser múltiplo de 100");
		else {
			int resto;

			int	cantidadDe1000 = monto/1000;
			resto = monto%1000*1000;
			int cantidadDe500 = resto/500;
			resto = resto%500*500;
			int cantidadDe100 = resto/100;

			if(billetesDe1000>=cantidadDe1000) 
				billetesDe1000 -=cantidadDe1000;
			else
				cantidadDe500 += cantidadDe1000*2;

			if(billetesDe500>=cantidadDe500) 
				billetesDe500 -=cantidadDe500;
			else
				cantidadDe100 += cantidadDe500*5;

			if(billetesDe100>=cantidadDe100) 
				billetesDe100 -=cantidadDe100;
			else
				return false;

			return true;
		}
	}
	/*Post: Se utiliza en casos de una transaccion entre 2 cuentas.
	 * Agrega movimientos a la lista de movimientos y a el historial de cada cuenta.*/
	private void setHistorialDeMovimientos(Cuenta cuenta, Cuenta cuenta2, String motivo, double monto) {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		Movimiento movimientoCuenta = new Movimiento(formatter.format(date), motivo, cuenta.getAlias(), monto);
		Movimiento movimientoCuenta2 = new Movimiento(formatter.format(date), motivo, cuenta2.getAlias(), monto);
		listaDeMovimientos.add(movimientoCuenta);
		listaDeMovimientos.add(movimientoCuenta2);
		cuenta.setMovimiento(movimientoCuenta);
		cuenta2.setMovimiento(movimientoCuenta2);
	}
	/*Post: Se utiliza en casos de una transaccion de una sola cuenta.
	 * Agrega el movimiento a la lista de movimientos y al historial de la cuenta*/
	private void setHistorialDeMovimientos(Cuenta cuenta, String motivo, double monto) {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		Movimiento movimientoCuenta = new Movimiento(formatter.format(date), motivo, cuenta.getAlias(), monto);
		listaDeMovimientos.add(movimientoCuenta);
		cuenta.setMovimiento(movimientoCuenta);
	}
	
	//----------------Carga de datos a memoria---------------------
	
	//Post: Se cargargan los datos a la base de datos 
	private void cargarDatos() throws IOException{
		cargarTarjetas();
		cargarMovimientos();
		cargarCuentas();
		cargarClientes();
	}
    //Post: Se cargan a la lista de tarjetas el archivo de tarjetas//
	private void cargarTarjetas() throws IOException{
		//Carga los datos del archivo de tarjetas.
		BufferedReader lector = new BufferedReader(new FileReader("datos/tarjetas.txt"));
		try {

			String linea;

			while ((linea=lector.readLine()) != null) {

				if (!linea.isEmpty()) {
					String[] datosTarjeta = linea.split(",");
					listaDeTarjetas.add(new Tarjeta(datosTarjeta[0],datosTarjeta[1],datosTarjeta[2]));
				}
			}

		} finally {

			lector.close();
		}


	}
	//Post: Se cargan a la lista de cuentas el archivo de cuentas//
	private void cargarCuentas() throws IOException{
		BufferedReader lector = new BufferedReader(new FileReader("datos/cuentas.txt"));
		try{

			String linea;

			while ((linea=lector.readLine()) != null) {

				if (!linea.isEmpty()) {
					String[] datosCuenta = linea.split(",");
					switch(datosCuenta[0]){
					case "01":
						listaDeCuentas.add(new CajaAhorroPesos(datosCuenta[1], Double.valueOf(datosCuenta[2]), agregarMovimientos(datosCuenta[1])));
						break;
					case "02":
						listaDeCuentas.add(new CuentaCorriente(datosCuenta[1], 
								Double.valueOf(datosCuenta[2]), Double.valueOf(datosCuenta[3]),agregarMovimientos(datosCuenta[1])));
						break;
					case "03":
						listaDeCuentas.add(new CajaAhorroDolares(datosCuenta[1], Double.valueOf(datosCuenta[2]), agregarMovimientos(datosCuenta[1])));	
						break;
					}
				}
			}

		} finally {

			lector.close();
		}

	}
	//Post: Se cargan a la lista de clientes el archivo de clientes//
	private void cargarClientes() throws IOException{
		BufferedReader lector = new BufferedReader(new FileReader("datos/clientes.txt"));
		try {

			String linea;

			while ((linea=lector.readLine()) != null) {

				if (!linea.isEmpty()) {
					String[] datosCliente = linea.split(",");
					Cliente nuevoCliente;
					listaDeClientes.add(nuevoCliente = new Cliente(datosCliente[0],datosCliente[1],
							buscarTarjetaPorCuit(datosCliente[0]), buscarCuentasPorAlias(datosCliente[1])));
					
				}
			}

		} finally {

			lector.close();
		}
	}
	//Post: Se cargan a la lista de clientes el archivo de clientes//	
	private void cargarMovimientos() throws IOException{
		BufferedReader lector = new BufferedReader(new FileReader("datos/movimientos.txt"));
		try {

			String linea;

			while ((linea=lector.readLine()) != null) {

				if (!linea.isEmpty()) {
					String[] datosMovimientos = linea.split(",");
					listaDeMovimientos.add(new Movimiento(datosMovimientos[0],datosMovimientos[1],datosMovimientos[2],Double.valueOf(datosMovimientos[3])));
				}
			}

		} finally {

			lector.close();
		}


	}
	//Post: Se cargan a la lista de clientes el archivo de clientes//	
	private ArrayList<Movimiento> agregarMovimientos(String alias){
		ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();

		for(Movimiento movimiento : listaDeMovimientos) {
			if(movimiento.getAlias().equals(alias)) {
				movimientos.add(movimiento);
			}
		}
		
		return movimientos;
	}
	/*Post: Se busca a la tarjeta por el cuit ingresado
	método utilizado para asiganarle a el cliente la tarjeta*/
	private Tarjeta buscarTarjetaPorCuit(String cuit) {
		
		for (Tarjeta tarjeta : listaDeTarjetas) {
			if (tarjeta.getCuit().equals(cuit)) {
				return tarjeta;
			}		
		}
		return null;
	}
	private Cuenta buscarCuentaPorAlias(String alias) throws ErrorDeTransaccion {
        for (Cuenta cuenta : listaDeCuentas) {
            if (cuenta.getAlias().equals(alias)) {
                return cuenta;
            }
        }
        throw new ErrorDeTransaccion("La cuenta destinataria no existe");
    }
	private ArrayList<Cuenta> buscarCuentasPorAlias(String alias) {
		ArrayList<Cuenta> cuentasDelCliente = new ArrayList();
        String[] aliasA =  alias.split("\\.");
        alias = aliasA[0]+"."+aliasA[1];
        for (Cuenta cuenta: listaDeCuentas){
            if(cuenta.getAlias().contains(alias))
                cuentasDelCliente.add(cuenta);
        }
        return cuentasDelCliente;
	}
	private Cliente buscarClientePorAlias(String alias) {
		for (Cliente cliente: listaDeClientes){
			if(cliente.getAlias().equals(alias))
				return cliente;
		}
		return null;
	}
	
	private String getAliasCliente(String cuit) {
		
		for (Cliente cliente : listaDeClientes) {
			if (cliente.getCuit().equals(cuit)) {
				return cliente.getAlias();
			}		
		}
		return null;
	}
	
	private Cuenta poseeCuenta(String tipoCuenta){
		Cuenta cuentafinal=null;
		for(Cuenta cuenta: currentCliente.getCuentas()){
			switch(tipoCuenta){
				case("pesos"):
					if(cuenta.getClass().equals(CajaAhorroPesos.class))
						cuentafinal = cuenta;
						break;
				case("dolares"):
					if(cuenta.getClass().equals(CajaAhorroDolares.class))
						cuentafinal = cuenta;
						break;
				case("corriente"):
					if(cuenta.getClass().equals(CuentaCorriente.class))
						cuentafinal = cuenta;
						break;
			}
			if(cuentafinal!=null)return cuentafinal;
		}
		return null;
	}
	
	private int getIntInput(){
		int numero = -1;
		while(numero<0) {
			try {
				numero = Integer.parseInt(teclado.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.println("Opción invalida. Intente nuevamente");
			}
		}
		return numero;
	}
	private double getDoubleInput(){
		double numero = -1;
		while(numero<0) {
			try {
				numero = Double.valueOf(teclado.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.println("Opción invalida. Intente nuevamente");
			}
		}
		return numero;
	}
	//---------------------------------------------------------------------
	
	public void consultarUltimosMovimientos(Cuenta cuenta) throws IOException{
        FileWriter impresor = new FileWriter("tikets\\tiketUltimosMovimientos.txt");
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        impresor.write("**");impresor.append("\n");
        System.out.println("**");System.out.println();
        System.out.println("Fecha             Motivo                  Monto");System.out.println();
        impresor.write("Fecha y Hora: "+formatter.format(date));impresor.append("\n");
        impresor.write("Cuenta de origen: "+cuenta.getAlias());impresor.append("\n");
        impresor.write("Ultimos movimientos: ");impresor.append("\n");
        for (String string : cuenta.getUltimosMovimientos()) {
            String[] stringA = string.split(",") ;
            System.out.println(stringA[0]+"__"+stringA[1]+"__"+stringA[3]);
            impresor.write("    "+stringA[0]+""+stringA[1]+""+stringA[3]);impresor.append("\n");
        }
        impresor.write("**");
        System.out.println();System.out.println("**");
        impresor.close();
    }
	
	//----------------Metodos utilizados para las pruebas-------------------------
	
	public Cuenta getCuenta( int indice) throws IOException{
		cargarCuentas();
		return listaDeCuentas.get(indice);
	}

	public Tarjeta getTarjeta(int indice) throws IOException{

		cargarTarjetas();
		return listaDeTarjetas.get(indice);
	}

	public Cliente getCliente(int indice) throws IOException{

		cargarClientes();
		return listaDeClientes.get(indice);
	}
    
	//----------------UI-----------------------------------------------------------
	public void cargarPantallaPrincipal()  throws IOException{
		System.out.println("\n\n********************************************");
		System.out.println("*  ingrese la operacion que desea realizar *");
		System.out.println("*                                          *");
		System.out.println("* 1- Consultas/solicitudes                 *");
		System.out.println("* 2- Extracciones/adelantos                *");
		System.out.println("* 3- Transferencia/deposito                *");
		System.out.println("* 4- Comprar dólares                       *");
		System.out.println("* 5- Cambiar cuenta actual                 *");
		System.out.println("* 0- Salir                                 *");
		System.out.println("********************************************");
		while(menuChoice!=0){
			menuChoice = getIntInput();
			switch(menuChoice){
			case 1:
				consultar();
				break;
			case 2:
				extraccionOadelanto();
				break;
			case 3:
				transferenciaOdeposito();
				break;
			case 4:
				System.out.println("Ingrese el monto a comprar: ");
                int monto = getIntInput();
                if(poseeCuenta("dolares")!=null) {
                	Cuenta cuentaDolares = poseeCuenta("dolares");
                	compraDeDolares(currentCuenta, cuentaDolares, monto);
                }
                cargarPantallaPrincipal();
                break;
			case 5:
				elegirCuenta();
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente");
				break;
			}
			
		}
	}
	public void transferenciaOdeposito() throws IOException{
		System.out.println("\n\n********************************************");
		System.out.println("*           Trasnferencia/Depósito         *");
		System.out.println("*  ingrese la operacion que desea realizar *");
		System.out.println("*                                          *");
		System.out.println("* 1- Transferencia     	                   *");
		System.out.println("* 2- Depósito                              *");
		System.out.println("* 3- Volver                                *");
		System.out.println("* 0- Salir                                 *");
		System.out.println("********************************************");
		while(menuChoice!=0){
			menuChoice = getIntInput();
			switch(menuChoice){
			case 1:
				System.out.println("Ingrese el alias de la cuenta destino: ");
				String alias = teclado.nextLine();
				System.out.println("Ingrese el monto a transferir: ");
				double montotransfer = getDoubleInput();
				transferencia(currentCuenta, alias, montotransfer);
				cargarPantallaPrincipal();
				break;
			case 2:
				System.out.println("Ingrese el monto a depositar: ");
				double monto = getDoubleInput();
				depositar(currentCuenta, Double.valueOf(monto));
				cargarPantallaPrincipal();
				break;
			case 3:
				cargarPantallaPrincipal();
			case 0:
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente");
				break;
			}
			
		}
	}
	public void extraccionOadelanto()throws IOException{
		System.out.println("\n\n********************************************");
		System.out.println("*  ingrese la operacion que desea realizar *");
		System.out.println("*                                          *");
		System.out.println("* 1- Extracción                            *");
		System.out.println("* 2- Volver                                *");
		System.out.println("* 0- Salir                                 *");
		System.out.println("********************************************");
		while(menuChoice!=0){
			menuChoice = getIntInput();
			switch(menuChoice){
			case 1:
				System.out.println("Ingrese el monto a retirar: ");
				
				int monto = 0;
				while(monto<100){
					monto = getIntInput();
				}
				retirar(currentCuenta, monto);
				cargarPantallaPrincipal();
				break;
			case 2:
				cargarPantallaPrincipal();
				break;
			case 4:
				elegirCuenta();
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente");
				break;
			}
			
		}
		
	}
	public void elegirCuenta()  throws IOException{
		
		while(menuChoice!=0){
			System.out.println("\n");
			System.out.println("*Elija la cuenta que va a utilizar         *");
			System.out.println("*1- Caja de ahorro en pesos                *");
			System.out.println("*2- Cuenta Corriente                       *");
			System.out.println("*3- Caja de ahorro en dólares              *");
			System.out.println("*0- Salir                                  *");
			System.out.println("********************************************");
			menuChoice = getIntInput();
			switch(menuChoice){
			case 1:
				if(poseeCuenta("pesos")!=null){
					currentCuenta = poseeCuenta("pesos");
					cargarPantallaPrincipal();
				}	
				else System.out.println("No posee ese tipo de cuenta");
				break;
			case 2:
				if(poseeCuenta("corriente")!=null) {
					currentCuenta = poseeCuenta("corriente");
					cargarPantallaPrincipal();
				}
				else System.out.println("No posee ese tipo de cuenta");
				break;
			case 3:
				if(poseeCuenta("dolares")!=null) {
					currentCuenta = poseeCuenta("dolares");
					cargarPantallaPrincipal();
				}
				else System.out.println("No posee ese tipo de cuenta");
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente");
				break;
			}
			
		}
	}
	
	
	public void consultar() throws IOException{
		while(menuChoice!=0){
			System.out.println("\n");
			System.out.println("*¿Qué conocimiento desea adquirir?         *");
			System.out.println("*1- Ver saldo de cuenta                    *");
			System.out.println("*2- Ver alias de cuenta                    *");
			System.out.println("*3- Ver últimos 10 movimientos             *");
			System.out.println("*4- Volver                                 *");
			System.out.println("*0- Salir                                  *");
			System.out.println("********************************************");
			menuChoice = getIntInput();
			switch(menuChoice){
			case 1:
				System.out.println("El saldo de su "+currentCuenta.tipoToString+" es de: " + currentCuenta.getSaldo());
				cargarPantallaPrincipal();
				break;
			case 2:
				System.out.println("El alias de su "+currentCuenta.tipoToString+" es: " + currentCuenta.getAlias());
				cargarPantallaPrincipal();
				break;
			case 3:
				System.out.println("Últimos movimientos de su "+currentCuenta.tipoToString+": ");
				consultarUltimosMovimientos(currentCuenta);
				cargarPantallaPrincipal();
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente");
				break;
			}
			
		}
	}
	
	

}
