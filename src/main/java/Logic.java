import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class Logic {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static String calculate(String input) {
        int j=0;
        Stack<String> data = new Stack<>();
        for(int i=0;i<input.length();i++) {
            if(input.charAt(i)!=')') {
                if(i<input.length()-1 && !(Character.isDigit(input.charAt(i)) && Character.isDigit(input.charAt(i+1)))) {
                    data.push(input.substring(j,i+1));
                    j=i+1;
                }else if(i==input.length()-1) {
                    data.push(input.substring(j,i+1));
                }
            } else {
                j=i+1;
                calculateEquation(data);
            }
        }
        if(data.size()>1) {
            calculateEquation(data);
        }
        return data.pop();
    }

    public static void calculateEquation(Stack<String> items){
        String poped = items.pop();
        BigDecimal num1;
        BigDecimal num2 = BigDecimal.ZERO;
        BigDecimal ans = BigDecimal.ZERO;
        try {
            while (!poped.equals("(")) {
                switch (poped) {
                    case "+":
                        num1 = new BigDecimal(items.pop());
                        ans = num1.add(num2);
                        break;
                    case "-":
                        num1 = new BigDecimal(items.pop());
                        ans = num1.subtract(num2);
                        break;
                    case "*":
                        num1 = new BigDecimal(items.pop());
                        ans = num1.multiply(num2);
                        break;
                    case "/":
                        num1 = new BigDecimal(items.pop());
                        ans = num1.divide(num2, 12, RoundingMode.HALF_UP);
                        break;
                    case "%":
                        BigDecimal percentage = new BigDecimal(items.pop());
                        String sign = items.pop();
                        num1 = new BigDecimal(items.pop());
                        BigDecimal percentageResult = num1.multiply(percentage).divide(BigDecimal.valueOf(100L),12,RoundingMode.HALF_UP);
                        items.push(num1.toString());
                        items.push(sign);
                        items.push(percentageResult.toString());
                        break;
                    default:
                        num2 = new BigDecimal(poped);
                }
                if(items.size()>0) {
                    poped = items.pop();
                } else {
                    break;
                }
            }
            items.push(ans.toString());
        } catch (ArithmeticException | NumberFormatException e) {
            LOGGER.error("Given String is not valid");
        } catch (Exception e) {
            LOGGER.error("Exception caught while calculating {} ",e);
        }
    }

}
