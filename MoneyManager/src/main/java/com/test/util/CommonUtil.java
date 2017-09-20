package com.test.util;

import java.math.BigDecimal;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class CommonUtil {

	public static float convertBigDecimaltoFloat(BigDecimal bigDecimalValue) {

		if (bigDecimalValue != null
				&& bigDecimalValue.compareTo(BigDecimal.ZERO) != 0) {
			return bigDecimalValue.floatValue();
		}
		return 0.0f;
	}
	
	public static void handleErrorMessage(String msg, String clientId) {
        String codingErrorMsgKey = "coding_error_msg";
        FacesMessage fm;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (org.apache.commons.lang3.StringUtils.isEmpty(msg)) {
            ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "bundle");
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(codingErrorMsgKey), bundle.getString(codingErrorMsgKey));
        }
        else {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        }
        facesContext.addMessage(clientId, fm);
    }

}
