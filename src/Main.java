
public class Main {
//Clase que se encarga de correr el programa
	
	private static Cadenas subcadena;
	
	private static String [] Entrada = {"bogt","bgootg", "tbgo", "ggbo", "ogtbogb"};
	
	private static int cota;
	
	public static void main(String [] cadenas) {
		//Para establecer la cota debemos tener encuenta que esta en es la suma de todas las 
		//posiciones de cada palabra en el arreglo de Entrada suponiendo que estas no se sobrelapan
		// la longitud de la cadena deberia ser 25.
		int suma =0;
		
		for(int i = 0; i< Entrada.length; i++)
		{
			suma+= Entrada[i].length();
		}
		
		subcadena= new 
	}
}
