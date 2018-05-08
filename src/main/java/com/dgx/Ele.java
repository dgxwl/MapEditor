package com.dgx;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 物体类型
 * @author Administrator
 *
 */
public enum Ele {
	@XStreamOmitField
	SKY,
	GROUND,
	BRICK,
	QUESTION,
	HARDBRICK,
	PIT,
	PIPE
}
