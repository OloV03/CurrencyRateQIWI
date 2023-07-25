package com.IlyaJukov.FreshQIWI.API;

public class Currency {

    private String name;
    private String engName;
    private String ParentCode;
    private String ISO_Num_Code;
    private String ISO_Char_Code;

    public String getCharCode() {
        return ISO_Char_Code;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public void setISO_Num_Code(String ISO_Num_Code) {
        this.ISO_Num_Code = ISO_Num_Code;
    }

    public void setISO_Char_Code(String ISO_Char_Code) {
        this.ISO_Char_Code = ISO_Char_Code;
    }
}
