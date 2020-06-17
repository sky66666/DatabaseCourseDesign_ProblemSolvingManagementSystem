package Utils;

public class Encryption {

    private final int x=23,k=7,rx=167,m=256;

    public static void main(String[] args){
        Encryption e = new Encryption();
        System.out.println(e.encrypt("password"));
        System.out.println(e.encrypt("123456"));
        System.out.println(e.decrypt(e.encrypt("0123456789abcdefghijklmnopqrstuvwxyz")));
    }

    public String encrypt(String s){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();++i){
            char c = s.charAt(i);
            if(c=='d'||c=='o'){
                sb.append(c);
            }else{
                sb.append((char)((c*x+k)%m));
            }

        }
        return sb.toString();
    }
    public String decrypt(String s){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();++i){
            char c = s.charAt(i);
            if(c=='d'||c=='o'){
                sb.append(c);
            }else {
                sb.append((char) (((c - k) * rx) % m));
            }
        }
        return sb.toString();
    }
}
