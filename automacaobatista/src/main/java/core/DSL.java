package core;
import static core.DriverFactory.getDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DSL {
	
	/********* TextField e TextArea ************/
	
	public void escrever(By by, String texto){
		getDriver().findElement(by).clear();
		getDriver().findElement(by).sendKeys(texto);
	}

	public void escrever(String id_campo, String texto){
		escrever(By.id(id_campo), texto);
	}
	
	public String obterValorCampo(By by) {
		getDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		return getDriver().findElement((by)).getAttribute("value");
	}
	
	/********* Radio e Check ************/
	
	public void clicarRadio(By by) {
		getDriver().findElement(by).click();
	}
	
	public void clicarRadio(String id) {
		clicarRadio(By.id(id));
	}
	
	public boolean isRadioMarcado(By by){
		
		return getDriver().findElement((by)).isSelected();
	}
	
	public void clicarCheck(By by) {
		clicarRadio((by));
	}
	
	public boolean isCheckMarcado(String id){
		return getDriver().findElement(By.id(id)).isSelected();
	}
	
	/********* Combo 
	 * @throws InterruptedException ************/
	
	public void selecionarCombo(By by, String valor) throws InterruptedException {
		Thread.sleep(5000);
		WebElement element = getDriver().findElement((by));
		Thread.sleep(5000);
		Select combo = new Select(element);
		Thread.sleep(5000);
		
		combo.selectByVisibleText(valor);
	}
	
	public void deselecionarCombo(String id, String valor) {
		WebElement element = getDriver().findElement(By.id(id));
		Select combo = new Select(element);
		combo.deselectByVisibleText(valor);
	}

	public String obterValorCombo(String id) {
		WebElement element = getDriver().findElement(By.id(id));
		Select combo = new Select(element);
		return combo.getFirstSelectedOption().getText();
	}
	
	public List<String> obterValoresCombo(String id) {
		WebElement element = getDriver().findElement(By.id("elementosForm:esportes"));
		Select combo = new Select(element);
		List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
		List<String> valores = new ArrayList<String>();
		for(WebElement opcao: allSelectedOptions) {
			valores.add(opcao.getText());
		}
		return valores;
	}
	
	public int obterQuantidadeOpcoesCombo(String id){
		WebElement element = getDriver().findElement(By.id(id));
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		return options.size();
	}
	
	public boolean verificarOpcaoCombo(String id, String opcao){
		WebElement element = getDriver().findElement(By.id(id));
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		for(WebElement option: options) {
			if(option.getText().equals(opcao)){
				return true;
			}
		}
		return false;
	}
	
	public void selecionarComboPrime(String radical, String valor) {
		clicarRadio(By.xpath("//*[@id='"+radical+"_input']/../..//span"));
		clicarRadio(By.xpath("//*[@id='"+radical+"_items']//li[.='"+valor+"']"));
	}
	
	/********* Botao ************/
	public void clicar(By by){
		
		getDriver().findElement(by).click();
	}
	
	public void clicarBotao(String id) {
		clicar(By.id(id));
	}
	
	public String obterValueElemento(String id) {
		return getDriver().findElement(By.id(id)).getAttribute("value");
	}
	
	/********* Link ************/
	
	public void clicarLink(String link) {
		getDriver().findElement(By.linkText(link)).click();
	}
	
	/********* Textos ************/
	
	public String obterTexto(By by) {
		return getDriver().findElement(by).getText();
	}
	
	public String obterTexto(String id) {
		return obterTexto(By.id(id));
	}
	
	/********* Alerts ************/
	
	public String alertaObterTexto(){
		Alert alert = getDriver().switchTo().alert();
		return alert.getText();
	}
	
	public String alertaObterTextoEAceita(){
		Alert alert = getDriver().switchTo().alert();
		String valor = alert.getText();
		alert.accept();
		return valor;
		
	}
	
	public String alertaObterTextoENega(){
		Alert alert = getDriver().switchTo().alert();
		String valor = alert.getText();
		alert.dismiss();
		return valor;
		
	}
	
	public void alertaEscrever(String valor) {
		Alert alert = getDriver().switchTo().alert();
		alert.sendKeys(valor);
		alert.accept();
	}
	
	/********* Frames e Janelas ************/
	
	public void entrarFrame(String id) {
		getDriver().switchTo().frame(id);
	}
	
	public void sairFrame(){
		getDriver().switchTo().defaultContent();
	}
	
	public void trocarJanela(String id) {
		getDriver().switchTo().window(id);
	}
	
	/************** JS *********************/
	
	public Object executarJS(String cmd, Object... param) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		return js.executeScript(cmd, param);
	}
	
	/************** Tabela *********************/
	
	public void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela) throws InterruptedException{
		int idLinha =-1;
		int idColunaBotao= -1;
		WebElement tabela = getDriver().findElement(By.xpath(idTabela));
		int idColuna = obterIndiceColuna(colunaBusca, tabela);
			
			
					
		while(idLinha==-1) 
		{
			
			idLinha = obterIndiceLinha(valor, tabela, idColuna);	
			
				if(idLinha==-1) 
				{				
				
					getDriver().findElement(By.linkText("Próximo ›")).click();
					Thread.sleep(1000);
					tabela = getDriver().findElement(By.xpath(idTabela));
				}
			}
		
		idColunaBotao = obterIndiceColuna(colunaBotao, tabela);
			
			
			
			
		WebElement celula = tabela.findElement(By.xpath(".//tr["+idLinha+"]/td["+idColunaBotao+"]"));

			
		celula.findElement(By.xpath("//td[11]/a")).click();
		
			
			
		}
		

		

		protected int obterIndiceLinha(String valor, WebElement tabela, int idColuna) {
					
			List<WebElement> linhas = tabela.findElements(By.xpath("./tbody/tr/td["+idColuna+"]"));
			int idLinha = -1;
			for(int i = 0; i < linhas.size(); i++) {
				if(linhas.get(i).getText().equals(valor)) {
					idLinha = i+1;
					break;
				}
			}
			
			
			return idLinha;
		}

		protected int obterIndiceColuna(String coluna, WebElement tabela) {
			List<WebElement> colunas = tabela.findElements(By.xpath(".//th"));
			int idColuna = -1;
			for(int i = 0; i < colunas.size(); i++) {
				if(colunas.get(i).getText().equals(coluna)) {
					idColuna = i+1;
					break;
				}
			}
			return idColuna;
		}
	}

