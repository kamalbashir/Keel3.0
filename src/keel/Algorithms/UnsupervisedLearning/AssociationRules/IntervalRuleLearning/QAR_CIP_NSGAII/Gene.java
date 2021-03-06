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

package keel.Algorithms.UnsupervisedLearning.AssociationRules.IntervalRuleLearning.QAR_CIP_NSGAII;

/**
 * <p>
 * @author Written by Diana Mart�n (dmartin@ceis.cujae.edu.cu) 
 * @version 1.1
 * @since JDK1.6
 * </p>
 */

import org.core.Randomize;

public class Gene {
	/**
	 * <p>
	 * It is used for representing and handling a Gene throughout the evolutionary learning
	 * </p>
	 */

	public static final int NOT_INVOLVED = -1;
	public static final int ANTECEDENT = 0;
	public static final int CONSEQUENT = 1;

	private int attr;
	private int ac;
	private boolean pn;
	private double lb;
	private double ub;

	/**
	 * <p>
	 * It creates a new gene
	 * </p>
	 */
	public Gene() {
	}

	/**
	 * <p>
	 * It allows to clone correctly a gene
	 * </p>
	 * @return A copy of the gene
	 */
	public Gene copy() {
		Gene gene = new Gene();

		gene.attr = this.attr;
		gene.ac = this.ac;
		gene.pn = this.pn;
		gene.lb = this.lb;
		gene.ub = this.ub;

		return gene;
	}

	/**
	 * <p>
	 * It does inversions for each part of a gene which were specifically designed for this algorithm
	 * </p>
	 * @param type The domain type of gene
	 * @param min_attr The minimum domain value depending on the type of gene
	 * @param max_attr The maximum domain value depending on the type of gene
	 */
	public void invert(int type, double min_attr, double max_attr) {		
		switch (this.ac) {
		case Gene.NOT_INVOLVED:
			this.ac = Gene.ANTECEDENT;
			break;
		case Gene.ANTECEDENT:
			this.ac = Gene.CONSEQUENT;
			break;
		case Gene.CONSEQUENT:
			this.ac = Gene.NOT_INVOLVED;
		}

		this.pn = !this.pn;

		if ( type != myDataset.NOMINAL ) {
			if ( type == myDataset.REAL ) {
				this.lb = Randomize.RandClosed() * (this.lb - min_attr) + min_attr;
				this.ub = Randomize.RandClosed() * (max_attr - this.ub) + this.ub;
			}
			else {
				this.lb = Randomize.RandintClosed((int)min_attr, (int)this.lb);
				this.ub = Randomize.RandintClosed((int)this.ub, (int)max_attr);
			}
		}
		else {
			if (this.lb == max_attr) this.lb = this.ub = min_attr;
			else {
				this.lb++;
				this.ub++;
			}
		}
	}

	/**
	 * <p>
	 * It returns whether a gene is involved in the chromosome being considered.
	 * In case it is involved, returns if it acts as antecedent or consequent
	 * </p>
	 * @return A constant value indicating the "role" played by the gene
	 */
	public int getActAs() {
		return this.ac;
	}

	/**
	 * <p>
	 * It sets whether a gene is involved in the chromosome being considered.
	 * In case it is involved, the user must specify if it acts as antecedent or consequent
	 * </p>
	 * @param ac The constant value indicating the "role" played by the gene
	 */
	public void setActAs(int ac) {
		this.ac = ac;
	}

	/**
	 * <p>
	 * It returns if a gene treats a positive or negative interval
	 * </p>
	 * @return A value indicating whether the interval must be considered as positive
	 */
	public boolean getIsPositiveInterval() {
		return this.pn;
	}

	/**
	 * <p>
	 * It sets if a gene treats positive or negative interval
	 * </p>
	 * @param pn The value indicating whether the interval must be considered as positive
	 */
	public void setIsPositiveInterval(boolean pn) {
		this.pn = pn;
	}


	/**
	 * <p>
	 * It returns the lower bound of the interval stored in a gene
	 * </p>
	 * @return A value indicating the lower bound of the interval
	 */
	public double getLowerBound() {
		return this.lb;
	}

	/**
	 * <p>
	 * It sets the lower bound of the interval stored in a gene
	 * </p>
	 * @param lb The value indicating the lower bound of the interval
	 */
	public void setLowerBound(double lb) {
		this.lb = lb;
	}

	/**
	 * <p>
	 * It returns the upper bound of the interval stored in a gene
	 * </p>
	 * @return A value indicating the upper bound of the interval
	 */
	public double getUpperBound() {
		return this.ub;
	}

	/**
	 * <p>
	 * It sets the upper bound of the interval stored in a gene
	 * </p>
	 * @param lb The value indicating the upper bound of the interval
	 */
	public void setUpperBound(double ub) {
		this.ub = ub;
	}

	/**
	 * <p>
	 * It indicates whether some other gene is "equal to" this one
	 * </p>
	 * @param obj The reference object with which to compare
	 * @return True if this gene is the same as the argument; False otherwise
	 */
	public boolean equals(Gene g) {
		if (g.attr == this.attr) {
			if (g.ac == this.ac) {
				if (g.pn == this.pn) {
					if (Math.abs(g.lb - this.lb) <= 0.00001) {
						if (Math.abs(g.ub - this.ub) <= 0.00001) return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * <p>
	 * It returns a string representation of a gene
	 * </p>
	 * @return A string representation of the gene
	 */  	
	public String toString() {
		return "Attr: " + attr + "AC: " + ac + "; PN: " + pn + "; LB: " + lb + "; UB: " + ub;
	}

	public int getAttr() {
		return this.attr;
	}

	public void setAttr(int var) {
		this.attr = var;
	}


	public int randAct () {
		return (Randomize.RandintClosed(Gene.NOT_INVOLVED, Gene.CONSEQUENT));
	}

	public void tuneInterval (myDataset dataset, int[] covered) {
		int i, nData;
		double min, max;
		double[] example;

		nData = dataset.getnTrans();
		min = this.ub;
		max = this.lb;

		for (i=0; i < nData; i++) {
			if (covered[i] > 0) {
				example = dataset.getExample(i);
				if (example[this.attr] < min)  min = example[this.attr];
				if (example[this.attr] > max)  max = example[this.attr];
			}
		}

		this.ub = max;
		this.lb = min;
	}


	public boolean isCover (int var, double value) {
		boolean covered;

		if (this.attr != var)  return (false);

		covered = true;

		if  (this.pn) {
			if ((value < this.lb) || (value > this.ub))  covered = false;
		}
		else {
			if ((value >= this.lb) && (value <= this.ub))  covered = false;
		}

		return (covered);
	}

	public boolean isSubGen (Gene gen) {
		if (this.attr != gen.attr)  return (false);
		if (this.pn != gen.pn)  return (false);
		if (this.ac != gen.ac)  return (false);
		if (Math.abs(gen.lb - this.lb) > 0.00001)  return (false);
		if (Math.abs(gen.ub - this.ub) > 0.00001)  return (false);

		return (true);
	}

}
