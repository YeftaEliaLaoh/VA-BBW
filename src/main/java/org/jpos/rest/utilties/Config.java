package org.jpos.rest.utilties;

public class Config
{
    public static int expires_in = 3600;

    //Response Code
    public static String Code_Success = "000";
    public static String Code_Header_not_complete = "001";
    public static String Code_Token_not_valid = "002";
    public static String Code_VA_not_found = "003";
    public static String Code_Signature_not_valid = "004";
    public static String Code_Client_ID_not_found = "005";
    public static String Code_General_Error = "006";

    //Response Desc
    public static String Desc_Success = "000";
    public static String Desc_Header_not_complete = "001";
    public static String Desc_Token_not_valid = "002";
    public static String Desc_VA_not_found = "003";
    public static String Desc_Signature_not_valid = "004";
    public static String Desc_Client_ID_not_found = "005";
    public static String Desc_General_Error = "006";

    public static String AUTHENTICATION_BASIC = "Basic";
    public static String AUTHENTICATION_BEARER = "Bearer";
}
