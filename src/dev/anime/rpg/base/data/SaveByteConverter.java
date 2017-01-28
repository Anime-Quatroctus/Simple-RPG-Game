package dev.anime.rpg.base.data;

import java.util.ArrayList;
import java.util.List;

import javafx.util.converter.ByteStringConverter;

public class SaveByteConverter {

	private static final ByteStringConverter converter = new ByteStringConverter();
	private static final byte CONTINUATION = 127;
	
	public static Object convertFromByteCode(Object[] bytes) {
		Object obj = null;
		if (bytes.length > 0) {
			switch ((byte) bytes[0]) {
			case 0:
				obj = bytes[1];
				break;
			case 1: 
			{
				Integer val = 0;
				for (int i = 1; i < bytes.length; i++) {
					if ((byte) bytes[i] != 127) val += (byte) bytes[i];
				}
				obj = val;
				break;
			}
			case 2:
			{
				Double val = 0.0D;
				for (int i = 1; i < bytes.length; i++) {
					if ((byte) bytes[i] != 127) val += (byte) bytes[i];
				}
				obj = val;
				break;
			}
			case 3:
			{
				Float val = 0.0F;
				for (int i = 1; i < bytes.length; i++) {
					if ((byte) bytes[i] != 127) val += (byte) bytes[i];
				}
				obj = val;
				break;
			}
			case 4:
			{
				Long val = 0L;
				for (int i = 1; i < bytes.length; i++) {
					if ((byte) bytes[i] != 127) val += (byte) bytes[i];
				}
				obj = val;
				break;
			}
			case 5:
			{
				StringBuilder builder = new StringBuilder();
				for (int i = 1; i < bytes.length; i++) {
					if ((byte) bytes[i] != 127)  builder.append(converter.toString((byte) bytes[i]));
				}
				obj = builder.toString();
				break;
			}
			}
		}
		System.out.println(obj);
		return obj;
	}
	
	public static Object[] convertToByteCode(Object obj) {
		List<Byte> returns = new ArrayList<Byte>();
		boolean numFlag = false;
		if (obj instanceof Integer) {
			returns.add((byte) 1);
			numFlag = true;
		} else if (obj instanceof Double) {
			returns.add((byte) 2);
			numFlag = true;
		} else if (obj instanceof Float) {
			returns.add((byte) 3);
			numFlag = true;
		} else if (obj instanceof Long) {
			returns.add((byte) 4);
			numFlag = true;
		} else if (obj instanceof String) {
			returns.add((byte) 5);
			for (int i = 0; i < ((String) obj).length(); i++) {
				returns.add(converter.fromString(((String)obj).substring(i, ((String) obj).length())));
				returns.add(CONTINUATION);
			}
			returns.remove(returns.size());
		} else if (obj instanceof Byte) {
			returns.add((byte) 0);
			numFlag = true;
		}
		if (numFlag) {
		long val = ((Number)obj).longValue();
			while (val >= 127) {
				returns.add((byte)126);
				returns.add(CONTINUATION);
				val -= 126;
			}
			returns.add((byte) val);
		}
		return returns.toArray();
	}
}