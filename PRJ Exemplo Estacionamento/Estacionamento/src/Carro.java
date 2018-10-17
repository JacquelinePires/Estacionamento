import java.io.IOException;
import java.util.List;

public class Carro extends Thread {

	private String letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String numero = "0123456789";
	private String placa;
	private Posicao posicao;
	private char intencao = 'E'; // (E)stacionar, (S)sair
	private int time = 200;

	public Carro() {
		placa = ""
				+ letra.charAt((int) (Math.random() * 1000) % letra.length())
				+ letra.charAt((int) (Math.random() * 1000) % letra.length())
				+ letra.charAt((int) (Math.random() * 1000) % letra.length()) 
				+ '-'
				+ numero.charAt((int) (Math.random() * 1000) % numero.length())
				+ numero.charAt((int) (Math.random() * 1000) % numero.length())
				+ numero.charAt((int) (Math.random() * 1000) % numero.length())
				+ numero.charAt((int) (Math.random() * 1000) % numero.length());
	}

	public Carro(String placa) {
		this.placa = placa;
	}

	public void run() {
		System.out.println("fire Carro...");
		while (!(posicao instanceof Saida)) {
			try {
				sleep(time + (int) (Math.random() * 300));
			} catch (InterruptedException e) {
			}
			// movimenta
			List<Posicao> list = posicao.getDestinos(this);
			int qtd = list.size();
			if (qtd > 0) {
				int irPara = (int) (Math.random() * 100) % qtd;
				try {
					list.get(irPara).colocaCarro(posicao.removeCarro());
					posicao = list.get(irPara);
					if (posicao instanceof Via) {
						time = 5 * Main.velocidade;
					}
					if (posicao instanceof Vaga) {
						time = 10000 * Main.velocidade;
						setIntencao('S');
					}
					if (posicao instanceof Entrada) {
						time = 2 * Main.velocidade;
					}
					if (posicao instanceof Saida) {
						time = 2 * Main.velocidade;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("" + placa + " saiu...");
		try {
			sleep(time + (int) (Math.random() * 30));
		} catch (InterruptedException e) {
		}
		posicao.removeCarro();
	}

	public String getPlaca() {
		return placa;
	}

	public Posicao getPosicao() {
		return posicao;
	}

	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}

	public char getIntencao() {
		return intencao;
	}

	public void setIntencao(char intencao) {
		this.intencao = intencao;
	}

}
