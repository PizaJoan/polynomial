
public class Polynomial {
    float [] nums = { 0 };

    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
        this.nums = new float[1];
        this.nums[0] = 0;
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        this.nums = new float[cfs.length];
        for (int i = 0; i < cfs.length; i++) {
            this.nums[i] = cfs[i];
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        float signe = 1;
        String [] original = s.split(" ");
        for (String ss: original) {
            if (ss.equals("+")) {
                signe = 1;
                continue;
            }
            if (ss.equals("-")) {
                signe = -1;
                continue;
            }

            int exponent = treuExp(ss);
            float coeficient = treuCoe(ss)*signe;
            setCoef(coeficient,exponent);
        }
        this.nums = flipIt(this.nums);
    }

    private float [] flipIt(float [] p) {
        for (int i = 0; i < p.length / 2; i++) {
            float aux = p[i];
            p[i] = p[p.length-i-1];
            p[p.length-i-1] = aux;
        }
        return p;
    }

    private void setCoef(float coef, int expo) {
        if (expo >= this.nums.length) {
            float [] aux = new float[expo+1];
            aux[expo] += coef;
            for (int i = 0; i < aux.length; i++) {
                if (i <= this.nums.length-1) {
                    aux[i] += this.nums[i];
                }
            }
            this.nums = aux;
        } else {
            this.nums[expo] += coef;
        }
    }

    private int treuExp(String s) {
        int exp = 0;
        if (s.contains("x") && !s.contains("^")) {
            exp = 1;
            return exp;
        }
        else if (!s.contains("x") && !s.contains("^")) {
            exp = 0;
            return exp;
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '^' ) {
                    exp = Integer.parseInt(s.substring(i+1));
                    return exp;
                }
            }
        }
        return exp;
    }

    private float treuCoe(String s) {
        float coe = 1;

        if (!s.contains("x") && !s.contains("^")) {
            coe = Float.parseFloat(s);
            return coe;
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'x' && i > 0) {
                    if (i > 0 && s.charAt(i-1) == '-') {
                        return -1;
                    }
                    coe = Float.parseFloat(s.substring(0, i));
                    return coe;
                }
            }
        }
        return coe;
    }
    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {

        if (this.nums.length != p.nums.length) {
            if (p.nums.length < this.nums.length) {
                p.nums =addNotEquals(p.nums, this.nums);
            } else {
                this.nums = addNotEquals(this.nums,p.nums);
            }

        }
        float [] sum = new float[p.nums.length];
            for (int i = 0; i < this.nums.length; i++) {
                sum[i] = p.nums[i]+this.nums[i];
            }
        Polynomial sumat = new Polynomial(sum);
        return sumat;
    }

    private float [] addNotEquals(float [] p, float [] p1) {
        float [] arraybe = new float[p1.length];
            for (int i = 0; i < p.length; i++) {
                    arraybe[i] = p[p.length-i-1];
            }
            flipIt(arraybe);
        return arraybe;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        return null;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {
       return null;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        return p.toString().equals(this.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] == 0) {
                if (i == 0 && this.nums[i] == 0 && this.nums.length > 0) {
                    if (this.nums.length > 1 && this.nums[1] > 0) {
                        continue;
                    }
                    return "0";
                }
                continue;
            }
            if (this.nums.length == 1) {
                if (this.nums[i] == 0 )  {
                    return "0";
                }
                if (this.nums[i] != 0) {
                    sb.append((int) this.nums[i]);
                    continue;
                }
            }
                if (this.nums.length == 2) {
                    if (this.nums[i] == 1 && i == 0) {
                        sb.append("x");
                        continue;
                    }
                    if (this.nums[i] == 0) {
                        sb.append("0");
                        continue;
                    }
                    if (this.nums[i] != 1 && i == 0) {
                        sb.append((int) this.nums[i] + "x");
                        continue;
                    }

                    if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] > 0 && i > 0) {
                        sb.append(" + " + (int) this.nums[i]);
                        continue;
                    }
                    if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] < 0 && i > 0) {
                        sb.append(" - " + (int) this.nums[i]*-1);
                        continue;
                    }
                }
            if (this.nums.length > 2 ) {
                if (this.nums[i] == 1 && this.nums.length-i == this.nums.length) {
                    sb.append("x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] == -1 && this.nums.length-i == this.nums.length) {
                    sb.append("-x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-i == this.nums.length) {
                    sb.append((int) this.nums[i] + "x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-i-1 > 1 && this.nums[i] > 0) {
                    sb.append(" + " + (int) this.nums[i] + "x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-i-1 > 1 && this.nums[i] < 0) {
                    sb.append(" - " + (int) this.nums[i]*-1 + "x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] == 1 && this.nums.length-i-1 > 1) {
                    sb.append(" + x^" + (this.nums.length-1-i));
                    continue;
                }
                if (this.nums[i] == -1 && this.nums.length-i-1 > 1) {
                    sb.append(" - x^" + (this.nums.length-1-i));
                    continue;
                }
                if (i > 0 && this.nums.length == 3 && this.nums[0] == 0 && this.nums[1] > 0 || this.nums[1] < 0) {
                    sb.append((int) this.nums[1] + "x");
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-1-i == 1 && this.nums[i] > 0) {
                    sb.append(" + " + (int) this.nums[i] + "x");
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-1-i == 1 && this.nums[i] < 0) {
                    sb.append(" - " + (int) this.nums[i]*-1 + "x");
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-1-i == 0 && this.nums[i] < 0) {
                    sb.append(" - " + (int) this.nums[i]*-1);
                    continue;
                }
                if (this.nums[i] != 0 && this.nums[i] != 1 && this.nums.length-1-i == 0 && this.nums[i] > 0) {
                    sb.append(" + " + (int) this.nums[i]);
                    continue;
                }
                if (!sb.toString().contains("0") && i > 0) {
                    sb.append("0");
                }
            }
        }
        return sb.toString();
    }
}
