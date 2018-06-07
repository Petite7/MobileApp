package sidebar;

public class SortModel {

    private String name;
    private String telephone;
    private String location;
    private String letters;//显示拼音的首字母

    public SortModel(String name, String telephone, String location){
        setName(name);
        this.telephone = telephone;
        this.location = location;
    }
    public SortModel(String name, String telephone){
        setName(name);
        this.telephone = telephone;
        this.location = "西安";
    }
    public SortModel(String name){
        setName(name);
        this.telephone = "15859505354";
        this.location = "西安";
    }
    public SortModel(){
        setName("小明");
        this.telephone = "15859505354";
        this.location = "西安";
    }

    public String getName() {
        return name;
    }
    public String getLetters() {
        return letters;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
        //汉字转换成拼音
        String pinyin = PinyinUtils.getPingYin(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            setLetters(sortString.toUpperCase());
        } else {
            setLetters("#");
        }
    }
    public void setLetters(String letters) {
        this.letters = letters;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
