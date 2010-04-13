package keel.Algorithms.RE_SL_Methods.P_FCS1;

/**
 * <p>
 * @author Written by Francisco Jos� Berlanga (University of Ja�n) 01/01/2007
 * @version 1.0
 * @since JDK 1.6
 * </p>
 */
 
public class Individual implements Comparable {
/**	
 * <p>
 * It contains the definition for a individual (which is form of several fuzzy rules)
 * </p>
 */
 
    Rule [] RuleBase;
    double fitness;
    int n_e;
    int num_reglas;

    /**
     * <p>       
     * Creates an individual containing "num" fuzzy rules, each one then with "tam" gaussian fuzzy sets
     * </p>       
     * @param num int The number of fuzzy rules in the individual
     * @param n_var int The number of gaussian fuzzy sets per each rule in the individual     
     */
    public Individual(int num, int n_var) {
        RuleBase = new Rule[num];
        for(int i = 0; i < num; i++){
            RuleBase[i] = new Rule(n_var);
        }
        fitness = -1.0;
        n_e = 1;
        num_reglas = num;
    }


    /**
     * <p>       
     * Creates an individual as a copy of another individual
     * </p>       
     * @param indi Individual The individual used to create the new individual
     */
    public Individual(Individual indi) {
        int tam = indi.RuleBase.length;
        RuleBase = new Rule[tam];
        for(int i = 0; i < tam; i++){
            RuleBase[i] = new Rule(indi.RuleBase[i]);
        }
        fitness = indi.fitness;
        n_e = indi.n_e;
        num_reglas = indi.num_reglas;
    }


    /**
     * <p>
     * Compares the fitness value of two individuals
     * </p>
     * @return int Returns -1 if the the fitness of the first individual is lesser than the fitness of the second one.
     * 1 if the the fitness of the first individual is greater than the fitness of the second one.
     * 0 if both individuals have the same fitness value
     */
    public int compareTo(Object a) {
        if (((Individual) a).fitness < this.fitness) {
            return -1;
        }
        if (((Individual) a).fitness > this.fitness) {
            return 1;
        }
        return 0;
    }

}
