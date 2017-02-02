

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
        String auxiliar;
        int grau = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '^') {
                grau = (int) s.charAt(i+1);
            }
        }
        float [] pol = new float[grau+1];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                if (s.charAt(i) < 0 || s.charAt(i) > 0) {
                    if (i > 0 && s.charAt(i-1) != '^') {
                        if (s.charAt(i) > 0) {
                            pol[i] = s.charAt(i);
                        }
                        if (s.charAt(i) < 0)
                            pol[i] = s.charAt(i)*-1;
                    }
                }
            }
        }
        this.nums = pol;

    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        return null;
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
            if (this.nums[i] == 0 && i == 0) {
                sb.append((int) this.nums[i]);
                continue;
            }
            if (this.nums.length == 1) {
                if (this.nums[i] == 0 )  {
                    sb.append((int) this.nums[i]);
                    continue;
                }
                if (this.nums[i] != 0) {
                    sb.append((int) this.nums[i]);
                }
            }
                if (this.nums.length == 2) {
                    if (this.nums[i] == 1 && i == 0) {
                        sb.append("x");
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
            if (this.nums.length > 2) {
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
            }
        }
        return sb.toString();
    }
}
