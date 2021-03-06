package edu.columbia.cs.psl.test.phosphor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import edu.columbia.cs.psl.phosphor.runtime.BoxedPrimitiveStoreWithObjTags;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.columbia.cs.psl.phosphor.struct.TaintedWithObjTag;

public class GetSetTaintObjTagITCase extends BaseMultiTaintClass{

	@Test
	public void testReferenceType() throws Exception {
		String s = "def";
		HashMap<Object, Object> m =  new HashMap<Object, Object>();
		MultiTainter.taintedObject(s, Taint.withLabel("a"));
		MultiTainter.taintedObject(m, Taint.withLabel("a"));

		int[] x = new int[10];
		//In its default mode, Phosphor tracks ONLY for the array ELEMENTS - not for the reference
		//can do -withArrayLengthTags to track a tag for the length of the array
		x = MultiTainter.taintedIntArray(x, "b");

		assertTrue(((TaintedWithObjTag)m).getPHOSPHOR_TAG() != null);
		assertTrue(MultiTainter.getTaint(m) != null);
		assertTrue(MultiTainter.getTaint(s) != null);
		assertTrue(MultiTainter.getTaint(x[0]) != null);
	}

	@Test
	public void testBoxing() {
		Boolean z = MultiTainter.taintedBoolean(false, "a");
		Byte b = MultiTainter.taintedByte((byte) 4,"a");
		Character c = MultiTainter.taintedChar('a', "a");
		Integer i = MultiTainter.taintedInt(4, "a");
		Short s = MultiTainter.taintedShort((short)5, "a");
		Long l = MultiTainter.taintedLong(5, "a");
		Float f = MultiTainter.taintedFloat(4f, "a");
		Double d = MultiTainter.taintedDouble(4d, "a");


		assertTrue(MultiTainter.getTaint(z.booleanValue()) != null);
		assertTrue(MultiTainter.getTaint(b.byteValue()) != null);
		assertTrue(MultiTainter.getTaint(c.charValue()) != null);
		assertTrue(MultiTainter.getTaint(i.intValue()) != null);
		assertTrue(MultiTainter.getTaint(s.shortValue()) != null);
		assertTrue(MultiTainter.getTaint(f.floatValue()) != null);
		assertTrue(MultiTainter.getTaint(l.longValue()) != null);
		assertTrue(MultiTainter.getTaint(d.doubleValue()) != null);
	}

	@Test
	public void testIntConstructorTaintsIntObject() {
		Integer i = new Integer(MultiTainter.taintedInt(5, "a"));
		assertTrue(MultiTainter.getTaint(i)!=null);
	}

	@Test
	public void testNotBoxing() {
		boolean z = MultiTainter.taintedBoolean(false, "a");
		byte b = MultiTainter.taintedByte((byte) 4, "a");
		char c = MultiTainter.taintedChar('a', "a");
		int i = MultiTainter.taintedInt(4, "a");
		short s = MultiTainter.taintedShort((short)5, "a");
		long l = MultiTainter.taintedLong(5, "a");
		float f = MultiTainter.taintedFloat(4f, "a");
		double d = MultiTainter.taintedDouble(4d, "a");


		assertTrue(MultiTainter.getTaint(z) != null);
		assertTrue(MultiTainter.getTaint(b) != null);
		assertTrue(MultiTainter.getTaint(c) != null);
		assertTrue(MultiTainter.getTaint(i) != null);
		assertTrue(MultiTainter.getTaint(s) != null);
		assertTrue(MultiTainter.getTaint(l) != null);
		assertTrue(MultiTainter.getTaint(f) != null);
		assertTrue(MultiTainter.getTaint(d) != null);
	}

	@Test
	public void testToString() {
		boolean z = MultiTainter.taintedBoolean(false, "a");
		byte b = MultiTainter.taintedByte((byte) 4, "a");
		char c = MultiTainter.taintedChar('a', "a");
		int i = MultiTainter.taintedInt(4, "a");
		short s = MultiTainter.taintedShort((short)5, "a");
		long l = MultiTainter.taintedLong(5, "a");
		float f = MultiTainter.taintedFloat(4f, "a");
		double d = MultiTainter.taintedDouble(4d, "a");
		assertNonNullTaint(Boolean.toString(z));
		assertNonNullTaint(Byte.toString(b));
		assertNonNullTaint(Character.toString(c));
		assertNonNullTaint(Short.toString(s));
		assertNonNullTaint(Long.toString(l));
		assertNonNullTaint(Float.toString(f));
		assertNonNullTaint(Double.toString(d));
		assertNonNullTaint(Integer.toString(i));
	}

	@Test
	public void testValueOf() {
		String hundred = new String(new char[]{'1','0','0'});
		Integer lbl = 5;
		String TRUE = new String(new char[]{'t','r','u','e'});
		((TaintedWithObjTag) ((Object) hundred)).setPHOSPHOR_TAG(Taint.withLabel(lbl));
		((TaintedWithObjTag) ((Object) TRUE)).setPHOSPHOR_TAG(Taint.withLabel(lbl));
		boolean z = Boolean.parseBoolean(TRUE);
		byte b = Byte.valueOf(hundred);
		byte b2 = Byte.parseByte(hundred);
		byte b3 = Byte.parseByte(hundred, 10);
		int i = Integer.valueOf(hundred);
		int i2 = Integer.valueOf(hundred, 10);
		int i3 = Integer.parseInt(hundred);
		int i4 = Integer.parseInt(hundred,10);
		int i5 = Integer.parseUnsignedInt(hundred);
		int i6 = Integer.parseUnsignedInt(hundred, 10);
		short s = Short.parseShort(hundred);
		short s2 = Short.parseShort(hundred, 10);
		short s3 = Short.valueOf(hundred);
		short s4 = Short.valueOf(hundred, 10);
		long l = Long.valueOf(hundred);
		long l2 = Long.valueOf(hundred, 10);
		long l3 = Long.parseLong(hundred);
		long l4 = Long.parseLong(hundred, 10);
		long l5 = Long.parseUnsignedLong(hundred);
		long l6 = Long.parseUnsignedLong(hundred, 10);
		float f = Float.parseFloat(hundred);
		float f2 = Float.valueOf(hundred);
		double d = Double.parseDouble(hundred);
		double d2 = Double.valueOf(hundred);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(z), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(b), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(b2), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(b3), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i2), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i3), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i4), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i5), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(i6), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(s), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(s2), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(s3), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(s4), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l2), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l3), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l4), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l5), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(l6), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(f), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(f2), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(d), lbl);
		assertTaintHasOnlyLabel(MultiTainter.getTaint(d2), lbl);
	}

	@Test
	public void testBoxedBooleanSameValueDifferentTags() {
		int len = 10;
		Boolean[] taintedValues = new Boolean[len];
		Boolean[] nonTaintedValues = new Boolean[len];
		for(int i = 0; i < len; i++) {
			taintedValues[i] = MultiTainter.taintedBoolean(true, i);
			nonTaintedValues[i] = true;
		}
		for(int i = 0; i < len; i++) {
			assertNullOrEmpty(MultiTainter.getTaint(nonTaintedValues[i]));
			Taint tag = MultiTainter.getTaint(taintedValues[i]);
			assertNonNullTaint(tag);
			assertArrayEquals(new Object[]{i}, tag.getLabels());
		}
	}

	@Test
	public void testBoxedByteSameValueDifferentTags() {
		int len = 10;
		Byte[] taintedValues = new Byte[len];
		Byte[] nonTaintedValues = new Byte[len];
		for(int i = 0; i < len; i++) {
			taintedValues[i] = MultiTainter.taintedByte((byte)5, i);
			nonTaintedValues[i] = 5;
		}
		for(int i = 0; i < len; i++) {
			assertNullOrEmpty(MultiTainter.getTaint(nonTaintedValues[i]));
			Taint tag = MultiTainter.getTaint(taintedValues[i]);
			assertNonNullTaint(tag);
			assertArrayEquals(new Object[]{i}, tag.getLabels());
		}
	}
	
	@Test
	public void testBoxedCharSameValueDifferentTags() {
		int len = 10;
		Character[] taintedValues = new Character[len];
		Character[] nonTaintedValues = new Character[len];
		for(int i = 0; i < len; i++) {
			taintedValues[i] = MultiTainter.taintedChar('a', i);
			nonTaintedValues[i] = 'a';
		}
		for(int i = 0; i < len; i++) {
			assertNullOrEmpty(MultiTainter.getTaint(nonTaintedValues[i]));
			Taint tag = MultiTainter.getTaint(taintedValues[i]);
			assertNonNullTaint(tag);
			assertArrayEquals(new Object[]{i}, tag.getLabels());
		}
	}

	@Test
	public void testBoxedShortSameValueDifferentTags() {
		int len = 10;
		Short[] taintedValues = new Short[len];
		Short[] nonTaintedValues = new Short[len];
		for(int i = 0; i < len; i++) {
			taintedValues[i] = MultiTainter.taintedShort((short)7, i);
			nonTaintedValues[i] = 7;
		}
		for(int i = 0; i < len; i++) {
			assertNullOrEmpty(MultiTainter.getTaint(nonTaintedValues[i]));
			Taint tag = MultiTainter.getTaint(taintedValues[i]);
			assertNonNullTaint(tag);
			assertArrayEquals(new Object[]{i}, tag.getLabels());
		}
	}
}
