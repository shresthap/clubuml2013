package controller.comparer.xmi;

import java.util.ArrayList;
import java.util.List;

public final class Utility {

	/**
	 * Gets the primitive element from the ArrayList of XmiTypeElement
	 * 
	 * @param array
	 *            of XmiTypeElement
	 * @param id
	 *            of target element
	 * @return XmiTypeElement if found, else ""
	 */
	public static String getPrimitiveNameById(ArrayList<XmiTypeElement> array,
			String id) {

		for (XmiTypeElement element : array) {
			if (element.getId().equals(id)) {
				return element.getName();
			}
		}
		return "";
	}

	/**
	 * Gets the Class element from the ArrayList of XmiClassElement
	 * 
	 * @param array
	 *            of XmiClassElement
	 * @param id
	 *            of target element
	 * @return XmiClassElement if found, else ""
	 */
	public static String getClassNameById(ArrayList<XmiClassElement> array,
			String id) {

		for (XmiClassElement element : array) {
			if (element.getId().equals(id)) {
				return element.getName();
			}
		}
		return "";
	}

	/**
	 * Gets the Base name from the ArrayList of XmiBaseElement
	 * 
	 * @param array
	 *            of XmiBaseElement
	 * @param id
	 *            of target element
	 * @return XmiBaseElement if found, else ""
	 */
	public static String getBaseNameById(ArrayList<XmiBaseElement> array,
			String id) {

		for (XmiBaseElement element : array) {
			System.out.println(element.getId());
			if (element.getId().equals(id)) {
				return element.getName();
			}
		}
		return "";
	}

	public static XmiAssociationElement getAssociationById(
			ArrayList<XmiAssociationElement> array, String id) {

		for (XmiAssociationElement element : array) {
			System.out.println(element.getId());
			if (element.getId().equals(id)) {
				return element;
			}
		}
		return null;
	}

	public static XmiClassElement getClassById(List<XmiClassElement> array,
			String id) {
		for (XmiClassElement element : array) {
			if (element.getId().equals(id)) {
				return element;
			}
		}

		return null;
	}

	public static XmiClassElement getClassByName(List<XmiClassElement> array,
			String name) {
		for (XmiClassElement element : array) {
			if (element.getName().equals(name)) {
				return element;
			}
		}

		return null;
	}

	/**
	 * Compares 2 class elements' attributes
	 * 
	 * @param classElement1
	 * @param classElement2
	 * @return true if all attributes are the same, else false
	 */
	private boolean compareClassAttributes(XmiClassElement classElement1,
			XmiClassElement classElement2) {

		return false;
	}

	/**
	 * Compares 2 class elements' operations
	 * 
	 * @param classElement1
	 * @param classElement2
	 * @return true if all operations are the same, else false
	 */
	private boolean compareClassOperations(XmiClassElement classElement1,
			XmiClassElement classElement2) {

		return false;
	}
}