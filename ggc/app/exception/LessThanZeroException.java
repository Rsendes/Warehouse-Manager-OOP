package ggc.app.exception;

import pt.tecnico.uilib.menus.CommandException;

public class LessThanZeroException extends CommandException{
	public LessThanZeroException(){
		super("O número deve ser positivo.");
	}
}