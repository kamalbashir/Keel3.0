package keel.Algorithms.Neural_Networks.NNEP_Regr.listener;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import keel.Algorithms.Neural_Networks.NNEP_Common.NeuralNetIndividual;
import keel.Algorithms.Neural_Networks.NNEP_Common.algorithm.NeuralNetAlgorithm;
import keel.Algorithms.Neural_Networks.NNEP_Common.data.DoubleTransposedDataSet;
import keel.Algorithms.Neural_Networks.NNEP_Common.data.IAttribute;
import keel.Algorithms.Neural_Networks.NNEP_Common.mutators.parametric.ParametricMutator;
import keel.Algorithms.Neural_Networks.NNEP_Common.mutators.parametric.ParametricSRMutator;
import keel.Algorithms.Neural_Networks.NNEP_Common.neuralnet.INeuralNet;
import keel.Algorithms.Neural_Networks.NNEP_Common.problem.ProblemEvaluator;
import keel.Algorithms.Neural_Networks.NNEP_Regr.problem.errorfunctions.MSEErrorFunction;
import keel.Algorithms.Neural_Networks.NNEP_Regr.problem.errorfunctions.SEPErrorFunction;
import keel.Algorithms.Neural_Networks.NNEP_Regr.problem.regression.IRegressor;
import keel.Algorithms.Neural_Networks.NNEP_Regr.problem.regression.RegressionProblemEvaluator;
import net.sf.jclec.AlgorithmEvent;
import net.sf.jclec.IAlgorithmListener;
import net.sf.jclec.IEvaluator;
import net.sf.jclec.base.AbstractIndividual;


/**  
 * <p>
 * @author Written by Pedro Antonio Gutierrez Penia (University of Cordoba) 16/7/2007
 * @author Written by Aaron Ruiz Mora (University of Cordoba) 16/7/2007
 * @version 0.1
 * @since JDK1.5
 * </p>
 */

public class NeuralNetReporterRegr implements IAlgorithmListener<NeuralNetAlgorithm<NeuralNetIndividual>> {

	/**
	 * <p>
	 * Generation reporter of neural net algorithms
	 * </p>
	 */

	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Generated by Eclipse
	 * </p>
	 */

	private static final long serialVersionUID = 873929825900558241L;

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** MSE Error function */

	private MSEErrorFunction mseErrorFunction = new MSEErrorFunction();

	/** SEP Error function */

	private SEPErrorFunction sepErrorFunction = new SEPErrorFunction();

	/** KEEL headers of output files */

	private String header;

	/** Train result file */

	private String trainResultFile;

	/** Test result file */

	private String testResultFile;

	/** Best model result file */

	private String bestModelResultFile;

	/** Metadata information of output attribute for generating output files */

	private IAttribute outputAttribute;

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Empty constructor
	 * </p>
	 */

	public NeuralNetReporterRegr() {
		super();
	}


	/////////////////////////////////////////////////////////////////
	// -------------------- Implementing IAlgorithmListener interface
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * This event is fired when the algorithm has started its execution.
	 * </p>
	 * @param event An event containing a reference to the source algorithm.
	 */ 

	public void algorithmStarted(AlgorithmEvent<NeuralNetAlgorithm<NeuralNetIndividual>> event) {
		// do report
		doIterationReport(event.getAlgorithm());
	}

	/**
	 * <p>
	 * This event is fired when the algorithm has finished its execution.
	 * </p>
	 * @param event An event containing a reference to the source algorithm.
	 */ 

	@SuppressWarnings("unchecked")
	public void algorithmFinished(AlgorithmEvent<NeuralNetAlgorithm<NeuralNetIndividual>> event) {

		try 
		{

			NeuralNetAlgorithm<NeuralNetIndividual> algorithm = event.getAlgorithm();

			ProblemEvaluator evaluator = ((ProblemEvaluator) event.getAlgorithm().getEvaluator());
			PrintWriter print = new PrintWriter( new FileWriter ( trainResultFile ) );
			print.write(header);

			DoubleTransposedDataSet dataset = evaluator.getTrainData();
			double[] observedOutput = evaluator.getUnscaledTrainData().getAllOutputs()[0];

			IRegressor bestRegressor = (IRegressor) event.getAlgorithm().getBestIndividual().getGenotype();
			double[] predictedOutput = bestRegressor.operate(dataset.getAllInputs());

			// Unscale the outputs
			evaluator.getNormalizer().scale(predictedOutput, evaluator.getOutputInterval().getRight(), evaluator.getOutputInterval().getLeft(),
					evaluator.getUnscaledMax()[dataset.getNofinputs()], evaluator.getUnscaledMin()[dataset.getNofinputs()]);

			// Print train results		
			for(int i=0; i<dataset.getNofobservations(); i++){
				print.write(outputAttribute.show(observedOutput[i]) + " ");
				print.write(outputAttribute.show(predictedOutput[i]) + "\n");
			}

			print.close();

			// Print test results		
			print = new PrintWriter( new FileWriter ( testResultFile ) );
			print.write(header);

			dataset = evaluator.getTestData();

			observedOutput = evaluator.getUnscaledTestData().getAllOutputs()[0];
			predictedOutput = bestRegressor.operate(dataset.getAllInputs());

			// Unscale the outputs
			evaluator.getNormalizer().scale(predictedOutput, evaluator.getOutputInterval().getRight(), evaluator.getOutputInterval().getLeft(),
					evaluator.getUnscaledMax()[dataset.getNofinputs()], evaluator.getUnscaledMin()[dataset.getNofinputs()]);

			for(int i=0; i<dataset.getNofobservations(); i++){
				print.write(outputAttribute.show(observedOutput[i]) + " ");
				print.write(outputAttribute.show(predictedOutput[i]) + "\n");
			}

			print.close();



			// Print test results		
			print = new PrintWriter( new FileWriter ( bestModelResultFile ) );   			

			// Algorithm generation
			int generation = algorithm.getGeneration();

			//ParametricMutator of NeuralNetAlgorithm
			ParametricMutator<NeuralNetIndividual> parametricMutator = null;
			if(algorithm.getMutator1() instanceof ParametricMutator)
				parametricMutator = (ParametricMutator<NeuralNetIndividual>) algorithm.getMutator1();
			else if(algorithm.getMutator2() instanceof ParametricMutator)
				parametricMutator = (ParametricMutator<NeuralNetIndividual>) algorithm.getMutator2();

			print.write(renderGeneration(generation, event.getAlgorithm().getBestIndividual(), 
					null, parametricMutator, algorithm.getEvaluator()));

			print.close();
		}
		catch ( IOException e )
		{
			System.err.println( "Can not open the training output file: " + e.getMessage() );
		}
	}

	/**
	 * <p>
	 * This event is fired when the algorithm has finished a generation.
	 * </p>
	 * @param event An event containing a reference to the source algorithm.
	 */ 

	public void iterationCompleted(AlgorithmEvent<NeuralNetAlgorithm<NeuralNetIndividual>> event) {
		// do report
		doIterationReport(event.getAlgorithm());
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------- Getting and setting properties
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Returns file name where the best model obtained will be written
	 * </p>
	 * @return String File name
	 */

	public String getBestModelResultFile() {
		return bestModelResultFile;
	}

	/**
	 * <p>
	 * Sets file name where the best model obtained will be written
	 * </p>
	 * @param String File name
	 */

	public void setBestModelResultFile(String bestModelResultFile) {
		this.bestModelResultFile = bestModelResultFile;
	}

	/**
	 * <p>
	 * Returns file name where the testing results of best model 
	 * obtained will be written
	 * </p>
	 * @return String File name
	 */

	public String getTestResultFile() {
		return testResultFile;
	}

	/**
	 * <p>
	 * Sets file name where the testing results of best model 
	 * obtained will be written
	 * </p>
	 * @param String File name
	 */

	public void setTestResultFile(String testResultFile) {
		this.testResultFile = testResultFile;
	}

	/**
	 * <p>
	 * Returns file name where the training results of best model 
	 * obtained will be written
	 * </p>
	 * @return String File name
	 */

	public String getTrainResultFile() {
		return trainResultFile;
	}

	/**
	 * <p>
	 * Sets file name where the testing results of best model 
	 * obtained will be written
	 * </p>
	 * @param String File name
	 */

	public void setTrainResultFile(String trainResultFile) {
		this.trainResultFile = trainResultFile;
	}

	/**
	 * <p>
	 * Returns KEEL file header
	 * </p>
	 * @return String KEEL file header
	 */

	public String getHeader() {
		return header;
	}

	/**
	 * <p>
	 * Sets KEEL file header
	 * </p>
	 * @param String KEEL file header
	 */

	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * <p>
	 * Returns output attribute metadata
	 * </p>
	 * @return IAttribute Output attribute metadata
	 */

	public IAttribute getOutputAttribute() {
		return outputAttribute;
	}

	/**
	 * <p>
	 * Sets output attribute metadata
	 * </p>
	 * @param outputAttribute New output attribute metadata
	 */

	public void setOutputAttribute(IAttribute outputAttribute) {
		this.outputAttribute = outputAttribute;
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public Methods
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Outputs the information of an iteration to System.out
	 * </p>
	 * @param algorithm Algorithm that is exectuting currently
	 */

	@SuppressWarnings("unchecked")
	public void doIterationReport(NeuralNetAlgorithm<NeuralNetIndividual> algorithm){

		// Algorithm generation
		int generation = algorithm.getGeneration();

		//ParametricMutator of NeuralNetAlgorithm
		ParametricMutator<NeuralNetIndividual> parametricMutator = null;
		if(algorithm.getMutator1() instanceof ParametricMutator)
			parametricMutator = (ParametricMutator<NeuralNetIndividual>) algorithm.getMutator1();
		else if(algorithm.getMutator2() instanceof ParametricMutator)
			parametricMutator = (ParametricMutator<NeuralNetIndividual>) algorithm.getMutator2();

		//Output generation
		printGeneration(generation, algorithm.getBestIndividual(), null, parametricMutator, algorithm.getEvaluator());
		System.out.println("Generations without improving ==> Best: " + algorithm.getNogbest() + " ( " + algorithm.getCurrentBest() + " / " + algorithm.getPreviousBest() + " )" +
				"\n                                  Mean: " + algorithm.getNogmean() + " ( " + algorithm.getCurrentMean() + " / " + algorithm.getPreviousMean() + " )\n");		

	}

	/**
	 * <p>
	 * Outputs the information of a generation to System.out
	 * </p>
	 * @param nofgeneration Number of generation of the algorithm
	 * @param bestNnind Best NeuralNetIndividual of the algorithm
	 * @param bestCCRNnind Best CCR NeuralNetIndividual of the algorithm (if it is a Classification problem)
	 * @param parametricMutator ParametricMutator of the algorithm
	 * @param evaluator NeuralNetEvaluator to use in individual evaluation
	 */

	public void printGeneration(int nofgeneration, AbstractIndividual<INeuralNet> bestNnind, AbstractIndividual<INeuralNet> bestCCRNnind,
			ParametricMutator<NeuralNetIndividual> parametricMutator, IEvaluator evaluator)
	{
		System.out.println(renderGeneration(nofgeneration, bestNnind, bestCCRNnind, parametricMutator, evaluator));
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected Methods
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Renders a generation of the algorithm to a String
	 * </p>
	 * @param nofgeneration Number of generation of the algorithm
	 * @param bestNnind Best NeuralNetIndividual of the algorithm
	 * @param bestCCRNnind Best CCR NeuralNetIndividual of the algorithm (if it is a Classification problem)
	 * @param parametricMutator ParametricMutator of the algorithm
	 * @param evaluator NeuralNetEvaluator to use in individual evaluation
	 * 
	 * @return String String with the render of a generation
	 */

	protected String renderGeneration(int nofgeneration, AbstractIndividual<INeuralNet> bestNnind, AbstractIndividual<INeuralNet> bestCCRNnind,
			ParametricMutator<NeuralNetIndividual> parametricMutator, IEvaluator evaluator){

		String result = ("Generation " + nofgeneration +"\n");
		if(bestCCRNnind!=null){
			result+="=> Best CCR Individual:\n";
			result+=renderNeuralNetIndividual(bestCCRNnind, evaluator);
		}
		else{
			result+="=> Best Fitness Individual:\n";
			result+=renderNeuralNetIndividual(bestNnind, evaluator);			
		}
		if(parametricMutator!=null)
			result+=("AlphaInput " + parametricMutator.getAlphaInput() + " AlphaOutput " + parametricMutator.getAlphaOutput());
		if(parametricMutator instanceof ParametricSRMutator)
			result+=(" Success Ratio " + ((ParametricSRMutator<NeuralNetIndividual>) parametricMutator).getSuccessRatio());
		return result;
	}

	/**
	 * <p>
	 * Renders a NeuralNetIndividual to a String
	 * </p>
	 * @param nnind IGenotype<INeuralNet> to render
	 * @param evaluator NeuralNetEvaluator to use in individual evaluation
	 * 
	 * @return String String with the render of a NeuralNetIndividual
	 */

	@SuppressWarnings("unchecked")
	protected String renderNeuralNetIndividual(AbstractIndividual<INeuralNet> nnind, IEvaluator evaluator){

		String result = nnind.toString();
		result+="\n";

		result+=("Number of hidden neurons: " + nnind.getGenotype().getNofhneurons());
		result+=(" Number of effective links: "+ nnind.getGenotype().getNoflinks() + "\n");

		IRegressor regressor = (IRegressor) nnind.getGenotype();
		result+=("Train MSE: " + ((RegressionProblemEvaluator)evaluator).getTrainRegressionError( regressor, mseErrorFunction) + "\n");
		result+=("Test  MSE: " + ((RegressionProblemEvaluator)evaluator).getTestRegressionError( regressor, mseErrorFunction) + "\n");
		result+=("Train SEP: " + ((RegressionProblemEvaluator)evaluator).getTrainRegressionError( regressor, sepErrorFunction) + "\n");
		result+=("Test  SEP: " + ((RegressionProblemEvaluator)evaluator).getTestRegressionError( regressor, sepErrorFunction) + "\n");

		return result;
	}
}
