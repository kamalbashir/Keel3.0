package keel.Algorithms.Neural_Networks.NNEP_Common.neuralnet;

/**
 * <p>
 * @author Written by Pedro Antonio Gutierrez Penya, Aaron Ruiz Mora (University of Cordoba) 17/07/2007
 * @version 0.1
 * @since JDK1.5
 * </p>
 */

public class LinearLayer extends LinkedLayer {
	
	/**
	 * <p>
	 * Represents a neural net layer with all the nodes of LinearNeuron type
	 * </p>
	 */

	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 8771829690162564134L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Empty constructor
	 * </p>
	 */
	public LinearLayer() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------- Implementing Abstract Methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * New neuron for the layer
	 * </p>
	 * @return LinkedNeuron New neuron for the layer
	 */
    public LinkedNeuron obtainNewNeuron() {
        return new LinearNeuron();
    }
}
