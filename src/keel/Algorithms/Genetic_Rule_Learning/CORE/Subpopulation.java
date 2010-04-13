/**
 * <p>
 * @author Written by Juli�n Luengo Mart�n 13/02/2007
 * @version 0.1
 * @since JDK 1.5
 * </p>
 */
package keel.Algorithms.Genetic_Rule_Learning.CORE;

import java.util.ArrayList;

/**
 * <p>
 * This class represents a subpopulation of rules belonging to a same feature (class).
 * </p>
 */
public class Subpopulation {

	ArrayList<Chromosome> rules;
	int totalGenes;
	int Mu_next;
	
	/**
	 * <p>
	 * Default constructor. Initializes the structures.
	 * </p>
	 */
	public Subpopulation(){
		rules = new ArrayList<Chromosome>();
		totalGenes = 0;
		Mu_next = 0;
	}
	
	/**
	 * <p>
	 * Gets a rule from this subpopulation
	 * </p>
	 * @param i the index of the rule
	 * @return
	 */
	public Chromosome getRule(int i){
		return rules.get(i);
	}
	
	/**
	 * <p>
	 * Adds a rule to this subpopulation
	 * </p>
	 * @param c the new rule to be added
	 */
	public void addRule(Chromosome c){
		rules.add(c);
		totalGenes+=c.getNumGenes();
	}
	
	/**
	 * <p>
	 * Removes one rule from the subpopulation list
	 * </p>
	 * @param c the rule to be removed
	 */
	public void removeRule(Chromosome c){
		rules.remove(c);
		totalGenes-=c.getNumGenes();
	}
	
	/**
	 * <p>
	 * Gets the TOTAL number of genes of this subpopulation
	 * </p>
	 * @return the number of genes
	 */
	public int getNumGenes(){
		return totalGenes;
	}
	
	/**
	 * <p>
	 * Gets the number of rules of this subpopulation
	 * </p>
	 * @return the number of rules
	 */
	public int getNumRules(){
		return rules.size();
	}
	
	/**
	 * <p>
	 * Mutates a specific rule and gene
	 * </p>
	 * @param rule rule which contains the gene to mutate
	 * @param gene the gene to be mutated
	 */
	public void mutate(int rule,int gene){
		rules.get(rule).mutateGene(gene);
	}

}
