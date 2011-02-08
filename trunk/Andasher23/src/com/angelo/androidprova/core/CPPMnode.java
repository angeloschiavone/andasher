package com.angelo.androidprova.core;

import java.io.Serializable;

import android.util.Log;

class CPPMnode implements Serializable {

	public CPPMnode child;
	public CPPMnode next;
	public CPPMnode vine;
	public short count;
	public int symbol;
	
	public int id;
	
	static int CPPMNOdeCount = 0;

	/*
	 * CSFS: Found that the C++ code used a short to represent a symbol in
	 * certain places and an int in others. As such, I've changed it to int
	 * everywhere. This ought to cause no trouble except in the case that
	 * behaviour on overflow is relied upon.
	 */

	public CPPMnode(int sym) {

		Log.e("CPPMnode sym", "CPPMNOdeCount" + (CPPMNOdeCount++));
		child = null;
		next = null;
		vine = null;
		count = 1;
		symbol = sym;
	}

	public CPPMnode() {
		// Log.e("CPPMnode null", "CPPMNOdeCount" + (CPPMNOdeCount++));
		child = null;
		next = null;
		vine = null;
		count = 1;
		id = CPPMNOdeCount++;
	}

	
	public boolean isLeaf() {
		return child == null;
	}

	public CPPMnode find_symbol(int sym) // see if symbol is a child of node
	{
		CPPMnode found = child;

		/*
		 * CSFS: I *think* this is supposed to be a pointer-copy but I'm not
		 * perfectly sure. If the find_symbol method fails, this may need to
		 * become a .clone()
		 */

		while (found != null) {
			if (found.symbol == sym) {
				return found;
			}
			found = found.next;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("CHILD_LIST: ");
		int childCount = 0;
		CPPMnode found = child;
		while (found != null) {
			childCount++;
			sb.append(found.symbol+" ");
			found = found.next;
		}
		
		// TODO Auto-generated method stub
		return "symbol " + (symbol + 0x60)
				+ (vine == null ? "" : (" vine " + vine.symbol))
				+ " childCount " + childCount + " count " + count + sb;
	}

	/*
	 * private void writeObject(ObjectOutputStream out) throws IOException {
	 * 
	 * out.writeObject(child); out.writeObject(next); out.writeObject(vine);
	 * out.writeInt(symbol); out.writeInt(count);
	 * 
	 * }
	 * 
	 * private void readObject(ObjectInputStream in) throws IOException,
	 * ClassNotFoundException {
	 * 
	 * child = (CPPMnode) in.readObject(); next = (CPPMnode) in.readObject();
	 * vine = (CPPMnode) in.readObject(); symbol = in.readInt(); count =
	 * in.readShort(); }
	 */

}