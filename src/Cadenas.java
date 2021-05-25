import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cadenas {

	//Input data
	private int cota;
	private String CadenaVerificacion;
	private String [] ArregloCadena ;
	
	/**
	 * Busca una solución optima para el arreglo dado de cadenas.
	 * @param totalValue
	 * @param ArregloCadeno
	 * @return
	 */
	public String findOptimalString( String [] Arreglo) {
		//Input data is saved as class attributes to avoid passing the parameters through the different methods
		this.ArregloCadena = ArregloCadena;
		String s1;
		String s2;
		//To limit the number of solutions that will be compared in the cycle below
		//the suboptimal solution provided the greedy algorithm would be used as upper bound 
		EncontrarOverlappingPair( s1, s2);
		StringChangeState opt = new StringChangeState(EncontrarOverlappingPair( s1, s2)); 
		int maxCoins = opt.getTotalCoins()-1;
		int minCoins = 0;
		while(minCoins<maxCoins) {
			int boundNumberOfCoins = (minCoins+maxCoins)/2;
			//Call of the graph exploration algorithm to find feasible solutions
			StringChangeState solution = findFeasibleSolution(boundNumberOfCoins);
			if(solution==null) {
				minCoins = boundNumberOfCoins+1;
			} else {
				opt = solution;
				maxCoins = boundNumberOfCoins-1;
			}
		}
		return opt.getCoins();
	}
	
	/**
	 * Metodo encontrar el minimo entre dos numeros
	 * @param C
	 * @param cota
	 * @return
	 */
	    static String str;
	    //Funcion de Utilidad para calcular el menor entre dos 
	    static int min(int a, int b)
	    {
	        return Math.min(a, b);
	    }
	 
	    /**
	     * Funcion para calcular el maximo, la superposicion en dos strings dados.
	     * @param str1
	     * @param str2
	     * @return
	     */
	    static int EncontrarOverlappingPair(String str1, String str2)
	    {
	         
	        int max = Integer.MIN_VALUE;//Max guarda el maximo
	        int len1 = str1.length();//Longitud del str1
	        int len2 = str2.length();//Longitud del str2
	 
	        // check suffix of str1 matches
	        // with prefix of str2
	        for (int i = 1; i <= min(len1, len2); i++)
	        {
	            // compare last i characters
	            // in str1 with first i
	            // characters in str2
	            if (str1.substring(len1 - i).compareTo(str2.substring(0, i)) == 0)
	            {
	                if (max < i)
	                {	 
	                    // Update max and str
	                    max = i;
	                    str = str1 + str2.substring(i);
	                }
	            }
	        }
	 
	        // check prefix of str1 matches
	        // with suffix of str2
	        for (int i = 1; i <=
	                           min(len1, len2); i++)
	        {	 
	            // compare first i characters
	            // in str1 with last i
	            // characters in str2
	            if (str1.substring(0, i).compareTo(
	                      str2.substring(len2 - i)) == 0)
	            {
	                if (max < i)
	                {
	                    // pdate max and str
	                    max = i;
	                    str = str2 + str1.substring(i);
	                }
	            }
	        }
	 
	        return max;
	    }
	 
	    // Function to calculate smallest
	    // string that contains
	    // each string in the given set as substring.
	    static String findShortestSuperstring(
	                          String arr[], int len)
	    { 
	        // run len-1 times to consider every pair
	        while (len != 1)
	        {
	             // To store maximum overlap
	            int max = Integer.MIN_VALUE;
	           
	            // To store array index of strings
	            // involved in maximum overlap
	            int l = 0, r = 0;
	                  
	            // to store resultant string after
	            // maximum overlap
	            String resStr = "";
	 
	            for (int i = 0; i < len; i++)
	            {
	                for (int j = i + 1; j < len; j++)
	                {
	 
	                    // res will store maximum
	                    // length of the matching
	                    // prefix and suffix str is
	                    // passed by reference and
	                    // will store the resultant
	                    // string after maximum
	                    // overlap of arr[i] and arr[j],
	                    // if any.
	                    int res = EncontrarOverlappingPair(arr[i], arr[j]);
	 
	                    // Check for maximum overlap
	                    if (max < res)
	                    {
	                        max = res;
	                        resStr = str;
	                        l = i;
	                        r = j;
	                    }
	                }
	            }
	 
	            // Ignore last element in next cycle
	            len--;
	 
	            // If no overlap,
	            // append arr[len] to arr[0]
	            if (max == Integer.MIN_VALUE)
	                arr[0] += arr[len];
	            else
	            {
	               
	                // Copy resultant string
	                // to index l
	                arr[l] = resStr;
	               
	                // Copy string at last index
	                // to index r
	                arr[r] = arr[len];
	            }
	        }
	        return arr[0];
	    }
	 
	   
	
	private StringChangeState findFeasibleSolution(String []C, int cota) {
		StringChangeState answer = null;
		//Initial state
		String [] Cadena = new String [C.length];
		Arrays.fill(Cadena, 0);
		StringChangeState state = new StringChangeState("");
		//Agenda
		Queue<StringChangeState> agenda = new LinkedList<>();
		agenda.add(state);
		while(agenda.size()>0 && answer == null) {
			//Choose next state from the agenda
			state = agenda.poll();
			if(contieneTodas(state)) {
				if(isSolution(state, cota)) {
					answer = state;
				} else {
					//Add successors to the agenda
					List<StringChangeState> successors = getSuccessors (state);
					agenda.addAll(successors);
				}
			}
		}
		return answer;
	}
	/**
	 * Calculates the successors of the given state. Successors are all states formed adding
	 * one coin of each denomination
	 * @param state source state to define successors
	 * @return List<CoinChangeState> successors of the given state
	 */
	private List<StringChangeState> getSuccessors(StringChangeState state) {
		String cadenas = state.getTotalChains();
		List<StringChangeState> ListaSucesores = new ArrayList<>(cadenas.length());
		for(int i=0; i<cadenas.length();i++) {
			StringChangeState suc = new StringChangeState(cadenas);
			ListaSucesores.add(suc);
		}
		return ListaSucesores;
	}
	
	
	/**
	 * Determines if the given state could lead to a solution. This function implements the branch and
	 * bound strategy within the graph exploration algorithm
	 * @param state that will be checked for viability. 
	 * @return boolean true if the total value of the given state is less or equal than the value to be completed
	 */
	private boolean Subcadena(String S1, String S2 ) {
		boolean cumple= false;
		int longitudS1 = S1.length();
		int longitudS2 =S2.length();
		
		for (int i = 0; i<= longitudS2-longitudS1;i++) {
			for(int j = 0; j< longitudS1; j++) {
				if(S2.charAt(i+j) == S1.charAt(j))
					break;
				else if(j== longitudS1)
					cumple= true;
			}
		}
		return cumple;
	}

	/**
	 * Determines if the given state is a solution. Implements the satisfiability predicate of the
	 * graph exploration algorithm
	 * @param state that will be checked
	 * @param boundNumberOfCoins Maximum number of coins allowed
	 * @return boolean true if the total value of the given state is equal to the value to be completed
	 */
	private boolean isSolution(StringChangeState state, int Cota) {
		return contieneTodas(state) && state.getTotalChains().length()<=Cota && state.getTotalChains().equals(CadenaVerificacion);
	}
	/**
	 * Calculates the total value of the given state taking into account the denominations
	 * @param state 
	 * @return
	 */
	private boolean contieneTodas(StringChangeState state) {
		boolean total = true;
		for(int i=0; i<ArregloCadena.length; i++) {
			if(!Subcadena(state.getTotalChains(), ArregloCadena[i])) { 
				total= false;
			}
		
			
		}
		return total;
	}
	
}
	/**
	 * An specific state for the graph exploration defined as a number of coins for each denomination
	 * 
	 */
class StringChangeState {
	String C;

	/**
	 * Creates a new state with the given configuration of coins
	 * @param coins Number of coins of each denomination.
	 * This array is copied internally to allow posterior modifications
	 */
	public StringChangeState(String C) {
		this.C = C;
	}
	/**
	 * Calculates the total number of coins in this state
	 * @return in Sum of the coins of each denomination
	 */
	public String getTotalChains () {
		
		return C;
		}
		
	
	/**
	 * Returns el arreglo de cadenas 
	 * @return String cadena que entra                                                                                                    [] number of coins of each denomination. The array is returned as is
	 */
	//public char [] getChains() {
		//return C;
	//}
	
}

