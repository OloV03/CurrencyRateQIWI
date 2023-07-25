package com.IlyaJukov.FreshQIWI.API;

import com.IlyaJukov.FreshQIWI.HTTP.ControllerHTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;

public class CentralBank {

    ControllerHTTP http = new ControllerHTTP();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private HashMap<String, Currency> CurrenciesHash;

    // API
    private String QUOTES = "http://www.cbr.ru/scripts/XML_daily.asp";
    private String CURRENCIES = "https://www.cbr.ru/scripts/XML_valFull.asp";
    private String QUOTECUR = "https://www.cbr.ru/scripts/XML_dynamic.asp";

    public HashMap<String, Currency> getСurrencies() {

        if (CurrenciesHash != null) {
            return CurrenciesHash;
        }

        Document currencies = http.get(CURRENCIES);
        CurrenciesHash =  getCurrenciesList(currencies);

        return CurrenciesHash;
    }

    public String getCurrencyRate(String curCode, Date date) {

        HashMap<String, String> params = http.newParams();

        Currency currency = getCurrencyByCode(curCode);
        params.put("date_req1", formatter.format(date));
        params.put("date_req2", formatter.format(date));
        params.put("VAL_NM_RQ", currency.getParentCode().trim());

        Document response = http.get(QUOTECUR, params);
        return new Formatter().format(
                "%s (%s): %s",
                    currency.getCharCode(),
                    currency.getName(),
                    getCurrencyRate(response))
        .toString();
    }

    private HashMap<String, Currency> getCurrenciesList(Document doc) {
        NodeList root = doc.getFirstChild().getChildNodes();
        HashMap<String, Currency> result = new HashMap<>();

        for (int i = 0; i < root.getLength(); i++) {
            Currency cur = parseCurrency(root.item(i));
            result.put(cur.getCharCode(), cur);
        }

        return result;
    }
    
    private Currency parseCurrency(Node node) {
        NodeList childNode = node.getChildNodes();
        Currency cur = new Currency();

        for (int i = 0; i < childNode.getLength(); i++) {
            Node element = childNode.item(i);

            if (element.getNodeName().equals("Name")) {
                cur.setName(element.getTextContent());
            }
            if (element.getNodeName().equals("EngName")) {
                cur.setEngName(element.getTextContent());
            }
            if (element.getNodeName().equals("ParentCode")) {
                cur.setParentCode(element.getTextContent());
            }
            if (element.getNodeName().equals("ISO_Num_Code")) {
                cur.setISO_Num_Code(element.getTextContent());
            }
            if (element.getNodeName().equals("ISO_Char_Code")) {
                cur.setISO_Char_Code(element.getTextContent());
            }
            if (element.getNodeName().equals("ParentCode")) {
                cur.setParentCode(element.getTextContent());
            }

        }
        return cur;
    }

    private String getCurrencyRate(Document doc) {
        NodeList result = doc.getFirstChild().getFirstChild().getChildNodes();

        for (int i = 0; i < result.getLength(); i++) {
            if (result.item(i).getNodeName().equals("Value"))
                return result.item(i).getTextContent();
        }
        return "";
    }

    private Currency getCurrencyByCode(String currency) {

        HashMap<String, Currency> currencies = getСurrencies();

        if (currencies.containsKey(currency))
            return currencies.get(currency);
        else
            return null;
    }
}
