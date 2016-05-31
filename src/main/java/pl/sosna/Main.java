package pl.sosna;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Stream;

/**
 * <p>Created by rsosn on 30.05.2016.</p>
 *
 * @author rsosn
 */
public class Main {

	public static void main(String [ ] args) {
		String filePath = args[0];
		String atSeparator = "@";
		String colonSeparator = ":";
		String wordFilter = "amount";
		String plnFilter = "PLN";
		final BigDecimal[] sum = {BigDecimal.ZERO};

		MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null)   {
				if (strLine.isEmpty()) {
					continue;
				}
				Stream.of(strLine.split(atSeparator))
						.filter(s -> s.contains(wordFilter))
						.flatMap(amount -> Stream.of(amount.split(colonSeparator)))
						.filter(res -> !res.contains(wordFilter)).forEach(last -> {
							String afterReplace = last.replace(plnFilter, "").replace(",", ".").trim();
							Double money = Double.parseDouble(afterReplace);
							sum[0] = sum[0].add(BigDecimal.valueOf(money), mc);
						});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Suma kwot wynosi: " + sum[0].doubleValue());


	}
}
