package br.com.sales.support.panel.ssp.application;

public abstract class UseCase<INPUT, OUTPUT> {

    public abstract OUTPUT execute(INPUT input);

}
