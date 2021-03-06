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

package keel.Algorithms.Neural_Networks.NNEP_Common.data;

import net.sf.jclec.util.range.Closure;
import net.sf.jclec.util.range.Interval;

/**
 * <p>
 * @author Written by Amelia Zafra, Sebastian Ventura (University of Cordoba) 17/07/2007
 * @version 0.1
 * @since JDK1.5
 * </p>
 */


public class RealNumericalAttribute extends AbstractAttribute{
	
	/**
	 * <p>
	 * Real attributes
	 * </p>
	 */

	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = -6328412187422731602L;
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables 
	/////////////////////////////////////////////////////////////////

	/** Real values range */
	
	Interval interval;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Empty constructor
	 * </p>
	 */
	public RealNumericalAttribute(){
		
		super();		
		interval = null;
		
	}
	
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Adds an interval
	 * </p>
	 * @param interval Interval to be added
	 */
	protected void addInterval(Interval interval){
		
		this.interval = interval;
	}
	
	
	/////////////////////////////////////////////////////////////////
	// -------------------------- Overwriting AbstractAttribute methods
	/////////////////////////////////////////////////////////////////


	/**
	 * <p>
	 * Access to the attribute type
	 * </p>
	 * @return attribute name
	 */
	@Override
	public AttributeType getType() {
		return AttributeType.DoubleNumerical;
	}


	/**
	 * <p>
	 * Check if this internal attribute value is valid
	 * </p>
	 * @return true|false
	 */	
	@Override
	public boolean isValid(double internalValue) {
		return interval.contains(((Double) internalValue).doubleValue());
		
	}


	/**
	 * <p>
	 * Check if this external attribute value is valid
	 * </p>
	 * @param externalValue Value to check
	 * @return true|false
	 */	
	@Override
	public boolean isValid(Object externalValue) {
		return interval.contains(((Double) externalValue).doubleValue());
		
	}

	/**
	 * <p>
	 * Parse an  external value to obtain  the internal value of the 
	 * Attribute
	 * </p>
	 * @return The external value of the attribute
	 */
	@Override
	public double parse(String externalValue) {
		
		return Double.parseDouble(externalValue);
	}

	/**
	 * <p>
	 * Show an String which represents a given real value
	 * </p>
	 * @return The real value of the attribute
	 */	
	@Override
	public String show(double internalValue) {
		
		return new Double(internalValue).toString();

	}

	/**
	 * <p>
	 * Return an interval with the allowed values
	 * </p>
	 * @return Interval Interval that contains the allowed values
	 */	
	public Interval intervalValues(){
		if(interval == null){
			interval = new Interval(0,Double.MAX_VALUE, Closure.ClosedClosed);
		}
		
		return interval;
	}

}

