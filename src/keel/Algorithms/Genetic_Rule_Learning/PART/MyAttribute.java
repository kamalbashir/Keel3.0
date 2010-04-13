/**
 * <p>
 * @author Written by Crist�bal Romero Morales (University of Oviedo)  01/07/2008
 * @author Modified by Xavi Sol� (La Salle, Ram�n Llull University - Barcelona) 03/12/2008
 * @version 1.1
 * @since JDK1.2
 * </p>
 */


package keel.Algorithms.Genetic_Rule_Learning.PART;

import java.util.Vector;
import java.util.Enumeration;


public class MyAttribute {
/**
 * <p>
 * Class to implement an attribute
 * </p>
 */
	
	
  /** Continuous attribute. */
  public final static int CONTINUOUS = 0;

  /** Discret attribute. */
  public final static int DISCRET = 1;

  /** The name.*/
  private String name;

  /** The type. */
  private int type;

  /** Values of a list attribute. */
  private Vector values;

  /** The minor value of a numeric attribute. */
  private float bottom;

  /** The bigger value of a numeric attribute. */
  private float top;

  /** The index. */
  private int index;

  /** Is included in the inputs or outputs?. */
  private boolean used;

  /** Constructor for continuous attributes.
   *
   * @param attributeName the attribute's name.
   * @param attributeIndex the attribute's index.
   */
  public MyAttribute(String attributeName, int attributeIndex) {
    name = attributeName;
    index = attributeIndex;
    values = null;
    type = CONTINUOUS;
    used = false;
  }

  /** Constructor for discret attributes.
   *
   * @param attributeName The name of the attribute.
   * @param attributeValues The values of the attributes.
   * @param attributeIndex The index of the attribute.
   */
  public MyAttribute(String attributeName, Vector attributeValues, int attributeIndex) {
    name = attributeName;
    index = attributeIndex;
    type = DISCRET;
    values = new Vector(attributeValues.size());
    used = false;

    for (int i = 0; i < attributeValues.size(); i++) {
      Object store = attributeValues.elementAt(i);
      values.addElement(store);
    }
  }

  /** Function to get the index of a value in the list of values.
   *
   * @param value The value.
   *
   * @return The index of the value.
   */
  public final int valueIndex(String value) {
    int i = 0;
    if (!isDiscret()) {
      return -1;
    }

    Enumeration enum2 = values.elements();

    while (enum2.hasMoreElements()) {
      String element = (String) enum2.nextElement();

      if (element.equalsIgnoreCase(value)) {
        return i;
      }

      i++;
    }

    return -1;
  }

  /** Returns if the attribute is discret or not.
   * @return if the attribute is discret or not.
   */
  public final boolean isDiscret() {
    return (type == DISCRET);
  }

  /** Returns if the attribute is continuous or not.
   * @return if the attribute is continuous or not.
   */
  public final boolean isContinuous() {
    return (type == CONTINUOUS);
  }

  /** Returns the name of the attribute.
   * @return the name of the attribute.
   */
  public final String name() {
    return name;
  }

  /** Function to get the number of values of a discret attribute.
   *
   * @return The number of values of the attribute.
   */
  public final int numValues() {
    if (!isDiscret()) {
      return 0;
    } else {
      return values.size();
    }
  }

  /** Returns the value with the given index.
   *
   * @param valIndex The index of the value.
   * @return the value with the given index.
   */
  public final String value(int valIndex) {
    if (!isDiscret()) {
      return "";
    } else {
      Object val = values.elementAt(valIndex);

      return (String) val;
    }
  }

  /** Sets the range of a continuous attribute.
   *
   * @param minRange The minimum value of the range.
   * @param maxRange The maximum value of the range.
   */
  final void setRange(float minRange, float maxRange) {
    if (isDiscret()) {
      throw new IllegalArgumentException(
          "Can only set value of numeric attribute!");
    } else {
      bottom = minRange;
      top = maxRange;
    }
  }

  /** Sets the range of a continuous attribute.
   *
   * @param minRange The minimum value of the range.
   * @param maxRange The maximum value of the range.
   */
  final void setRange(int minRange, int maxRange) {
    if (isDiscret()) {
      throw new IllegalArgumentException(
          "Can only set value of numeric attribute!");
    } else {
      bottom = minRange;
      top = maxRange;
    }
  }

  /** Returns the minor value of a continuous attribute.
   * @return the minor value of a continuous attribute.
   */
  public final float getMinRange() {
    if (isDiscret()) {
      throw new IllegalArgumentException(
          "Can only set value of numeric attribute!");
    } else {
      return bottom;
    }
  }

  /** Gets the bigger value of a continuous attribute.
   * @return the bigger value of a continuous attribute.
   */
  public final float getMaxRange() {
    if (isDiscret()) {
      throw new IllegalArgumentException(
          "Can only set value of numeric attribute!");
    } else {
      return top;
    }
  }

  /** Sets the attribute as used.
   *
   */
  public void activate() {
    used = true;
  }

  /** Returns true if this attribute used in output or input clause.
   * @return true if this attribute used in output or input clause.
   */
  public boolean isActive() {
    return used;
  }
}
