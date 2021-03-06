/***********************************************************************

	This file is part of KEEL-software, the Data Mining tool for regression, 
	classification, clustering, pattern mining and so on.

	Copyright (C) 2004-2010
	
	F. Herrera (herrera@decsai.ugr.es)
    L. S�nchez (luciano@uniovi.es)
    J. Alcal�-Fdez (jalcala@decsai.ugr.es)
    S. Garc�a (sglopez@ujaen.es)
    A. Fern�ndez (alberto.fernandez@ujaen.es)
    J. Luengo (julianlm@decsai.ugr.es)

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see http://www.gnu.org/licenses/
  
**********************************************************************/

package keel.Algorithms.Neural_Networks.NNEP_Regr.problem.regression;



import java.util.Comparator;

import keel.Algorithms.Neural_Networks.NNEP_Common.data.DoubleTransposedDataSet;
import keel.Algorithms.Neural_Networks.NNEP_Common.problem.ProblemEvaluator;
import keel.Algorithms.Neural_Networks.NNEP_Common.problem.errorfunctions.IErrorFunction;
import net.sf.jclec.IConfigure;
import net.sf.jclec.IFitness;
import net.sf.jclec.base.AbstractIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.fitness.ValueFitnessComparator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;
/**  
 * <p>
 * @author Written by Pedro Antonio Gutierrez Penia (University of Cordoba) 16/7/2007
 * @author Written by Aaron Ruiz Mora (University of Cordoba) 16/7/2007
 * @version 0.1
 * @since JDK1.5
 * </p>
 */

public class RegressionProblemEvaluator extends ProblemEvaluator<AbstractIndividual<? extends IRegressor>> implements IConfigure {
	
	/**
	 * <p>
	 * Regression problem evaluator
	 * </p>
	 */
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 5628503640645851126L;
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Error Function
	/////////////////////////////////////////////////////////////////
	
	/** Error function to evaluate regressor */
	
	IErrorFunction<double[]>  defaultErrorFunction;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Fitness comparator
	/////////////////////////////////////////////////////////////////
	
	/** Fitnesses comparator */
	
	protected Comparator<IFitness> comparator = 
		new ValueFitnessComparator(false);
    
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////
    
    /**
     * <p>
     * Empty constructor
     * </p>
     */
    
    public RegressionProblemEvaluator() {
        super();
    }

	/////////////////////////////////////////////////////////////////
	// ------------------------------- Getting and setting properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Returns error function
	 * </p>
	 * @return IErrorFunction<double[]> Error function
	 */
	
	public IErrorFunction<double[]> getErrorFunction() 
	{
		return defaultErrorFunction;
	}

	/**
	 * <p>
	 * Sets error function
	 * </p>
	 * @param errorFunction error function
	 */
	
	public void setErrorFunction(IErrorFunction<double[]> errorFunction) 
	{
		this.defaultErrorFunction = errorFunction;
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting AbstractEvaluator methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Returns a ValueFitnessComparator
	 * </p> 
	 * @return Comparator
	 */
	
	public Comparator<IFitness> getComparator() {
		return comparator;
	}

	/**
	 * <p>
	 * Evaluate a individual
	 * </p>
	 * @param ind Individual
	 * 
	 */
	public void evaluate(AbstractIndividual<? extends IRegressor> ind) {
		// Dataset to be used
		DoubleTransposedDataSet dataset;
		if(dataNormalized)
			dataset = scaledTrainData;
		else
			dataset = unscaledTrainData;
		
		// Obtained outputs with this dataSet
		double obtained[] = ind.getGenotype().operate(dataset.getAllInputs());
		
		// Obtain error
		double error = defaultErrorFunction.calculateError(obtained,dataset.getOutput(0));
		
        //Set the fitness of the individual as (1/1+Error)
        ind.setFitness(new SimpleValueFitness(1/(1+error)));
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Other errors
	/////////////////////////////////////////////////////////////////
	
    /**
     * <p>
     * Returns the train error value of a neural net with an specified
     * error function
     * </p>
     *
     * @param regressor Neural net to obtain the error
     * @param errorFunction Error function to obtain the error
     * 
     * @return double Train error value
     */
    
    public double getTrainRegressionError(IRegressor regressor, IErrorFunction<double[]> errorFunction){    	
		
    	// Dataset to be used
		DoubleTransposedDataSet dataset;
		if(dataNormalized)
			dataset = scaledTrainData;
		else
			dataset = unscaledTrainData;
		
		// Obtained outputs with this dataSet
		double obtained[] = regressor.operate(dataset.getAllInputs());

		// Unscale the outputs if neccesary
        if(dataNormalized){    		
        	normalizer.scale(obtained, outputInterval.getRight(), outputInterval.getLeft(),
        			unscaledMax[dataset.getNofinputs()], unscaledMin[dataset.getNofinputs()]);
        	dataset = unscaledTrainData;        
        }
       	double error = errorFunction.calculateError(obtained,dataset.getOutput(0));
		
        // Return train error value
        return error;
    }
    
    /**
     * <p>
     * Returns the test error value of a neural net with an specified
     * error function
     * </p>
     * @param regressor Neural net to obtain the error
     * @param errorFunction Error function to obtain the error
     * 
     * @return double Test error value
     */
    
    public double getTestRegressionError(IRegressor regressor, IErrorFunction<double[]> errorFunction){    	
		
    	// Dataset to be used
		DoubleTransposedDataSet dataset;
		if(dataNormalized)
			dataset = scaledTestData;
		else
			dataset = unscaledTestData;
		
		// Obtained outputs with this dataSet
		double obtained[] = regressor.operate(dataset.getAllInputs());
        
    	// Unscale the outputs if neccesary
        if(dataNormalized){
    		// Unscale the outputs
        	normalizer.scale(obtained, outputInterval.getRight(), outputInterval.getLeft(),
        			unscaledMax[dataset.getNofinputs()], unscaledMin[dataset.getNofinputs()]);
        	dataset = unscaledTestData;        
        }
        double error = errorFunction.calculateError(obtained,dataset.getOutput(0));
		
        // Return test error value
        return error;
    }
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------- Implementing IConfigure interface
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Configuration parameters for NeuralNetEvaluator are:
	 * </p>
	 * <ul>
	 * <li>
	 * <code>Problem evaluator configuration</code></p> 
	 * net.sf.jclec.problem.ProblemEvaluator
	 * </li>
	 * <li>
	 * <code>error-function: complex</code></p> 
	 * Error function used for evaluating individuals.
	 * </li>
	 * </ul>
	 * @param settings Settings to Configure
	 */

	@SuppressWarnings("unchecked")
    public void configure(Configuration settings) {
    	
    	// ProblemEvaluator configuration
    	super.configure(settings);
    	
    	// Individual error function
		try {
			// Error function classname
			String errorFunctionClassname = 
				settings.getString("error-function");
			// Error function class
			Class<IErrorFunction<double[]>> errorFunctionClass = 
				(Class<IErrorFunction<double[]>>) Class.forName(errorFunctionClassname);
			// Error function instance
			IErrorFunction<double[]> errorFunction = errorFunctionClass.newInstance();
			// Set error function
			setErrorFunction(errorFunction);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal error function classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of error function", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of error function", e);
		}
    	
    }

}

