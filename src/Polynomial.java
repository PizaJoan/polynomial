import java.util.Arrays;

public class Polynomial {
    float[] nums = {0};

    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
        this.nums = new float[1];
        this.nums[0] = 0;
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        this.nums = new float[cfs.length];
        for (int i = 0; i < cfs.length; i++) {
            if (cfs[0] == 0) {
                for (int j = 0; j < cfs.length; j++) {
                    if (cfs[j] != 0) {
                        this.nums = new float[cfs.length - j];
                        for (int k = 0; k < cfs.length - j; k++) {
                            this.nums[k] = cfs[k + j];
                        }
                        break;
                    }
                }
            } else {
                this.nums[i] = cfs[i];
            }
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        float signe = 1;
        String[] original = s.split(" ");
        for (String ss : original) {
            if (ss.equals("+")) {
                signe = 1;
                continue;
            }
            if (ss.equals("-")) {
                signe = -1;
                continue;
            }
            int exponent = treuExp(ss);
            float coeficient = treuCoe(ss) * signe;
            if (coeficient == 0) {
                continue;
            }
            setCoef(coeficient, exponent);
        }
        flipIt(this.nums);
    }

    private void flipIt(float[] p) {
        for (int i = 0; i < p.length / 2; i++) {
            float aux = p[i];
            p[i] = p[p.length - i - 1];
            p[p.length - i - 1] = aux;
        }
    }

    private void setCoef(float coef, int expo) {
        if (expo >= this.nums.length) {
            float[] aux = new float[expo + 1];
            aux[expo] = coef;
            for (int i = 0; i < aux.length; i++) {
                if (i <= this.nums.length - 1) {
                    aux[i] = this.nums[i];
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
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '^') {
                    exp = Integer.parseInt(s.substring(i + 1));
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
                    if (i > 0 && s.charAt(i - 1) == '-') {
                        return -1;
                    }
                    coe = Float.parseFloat(s.substring(0, i));
                }
            }
        }
        return coe;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        if (this.nums.length != p.nums.length) {
            if (p.nums.length < this.nums.length) {
                p.nums = addNotEquals(p.nums, this.nums);
            } else {
                this.nums = addNotEquals(this.nums, p.nums);
            }
        }
        float[] sum = new float[p.nums.length];
        for (int i = 0; i < this.nums.length; i++) {
            sum[i] = p.nums[i] + this.nums[i];
        }
        Polynomial sumat = new Polynomial(sum);
        return sumat;
    }

    private float[] addNotEquals(float[] p, float[] p1) {
        float[] arraybe = new float[p1.length];
        for (int i = 0; i < p.length; i++) {
            arraybe[i] = p[p.length - i - 1];
        }
        flipIt(arraybe);
        return arraybe;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        float[] mult = new float[p2.nums.length + this.nums.length - 1];
        for (int i = 0; i < this.nums.length; i++) {
            float numactual = this.nums[i];
            if (numactual == 0) {
                continue;
            }
            for (int j = 0; j < p2.nums.length; j++) {
                mult[j + i] += numactual * p2.nums[j];
            }
        }
        Polynomial multiplicat = new Polynomial(mult);
        return multiplicat;
    }

    private Polynomial simpleMult(float num, int pos) {
        float[] res = new float[pos + this.nums.length];
        for (int i = 0; i < this.nums.length; i++) {
            if (i == this.nums.length) {
                break;
            }
            float actual = this.nums[i];
            if (actual == 0) {
                continue;
            }
            res[i] += num * actual;
        }
        Polynomial p2 = new Polynomial(res);
        return p2;
    }

    private void signeChange() {
        for (int i = 0; i < this.nums.length; i++) {
            this.nums[i] *= -1;
        }
    }

    private float getMax() {
        float num = 0;
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] != 0) {
                return this.nums[i];
            }
        }
        return num;
    }

    private int getExp() {
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] != 0) {
                return this.nums.length - i - 1;
            }
        }
        return this.nums.length - 1;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial divisor) {
        Polynomial residu = this;
        Polynomial cocient = new Polynomial();
        while (residu.getExp() >= divisor.getExp() && residu.nums[residu.getExp()] != 0) {
            float cf1 = residu.getMax();
            int exp1 = residu.getExp();

            float cf2 = divisor.getMax();
            int exp2 = divisor.getExp();

            float rescf = cf1 / cf2;
            int resexp = exp1 - exp2;

            cocient.setCoef(rescf, resexp);

            Polynomial resta = divisor.simpleMult(rescf, resexp);

            resta.signeChange();

            residu = residu.add(resta);
        }

        cocient.flipIt(cocient.nums);
        Polynomial[] divisio = {cocient, residu};
        return divisio;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        if (this.nums.length - 1 == 1) {
            float[] sol = soloX(this);
            return sol;
        } else if (this.nums.length - 1 >= 2) {
            if (this.nums.length - 1 == 2) {
                float[] sol = Arrel(this);
                return sol;
            } else if (this.nums.length - 1 > 2) {
                if (this.nums.length - 1 == 4 && this.nums[1] == 0 && this.nums[3] == 0 && this.nums[2] != 0) {
                    float [] sol = biquaD();
                    return sol;
                } else if (this.nums[1] == 0 ){
                    float[] sol = Arrelmasdos(this.nums);
                    return sol;
                } else {
                    float [] divisors = getDivisors(this.nums[this.nums.length-1]);
                    float [] sol = diferent(divisors);
                    return sol;
                }
            }
        }
        return null;
    }

    private float [] getDivisors(float num) {
        int cont = 0;
        for (int i = 0; i < num; i++) {
            if (num % i == 0) {
                cont++;
            }
        }
        float [] divisors = new float[cont];
        cont = 0;
        for (int i = 0; i < num; i++) {
            if (num % i == 0) {
                divisors[cont] = i;
                cont++;
            }
        }
        return divisors;
    }

    private void llevazeros() {
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] == 0) {
                float [] be = new float[i];
                for (int j = 0; j < be.length; j++) {
                    be[j] = this.nums[j];
                }
                this.nums = be;
            }
        }
    }

    private void ruffini(float num) {
        for (int i = 0; i < this.nums.length; i++) {
            this.nums[i] *= num;
            if (i < this.nums.length-1) {
                this.nums[i+1] += this.nums[i];
            } else {
                break;
            }
        }
    }

    private float [] diferent(float [] divisors) {
        float [] sol = new float[4];
        for (int i = 0; i < divisors.length; i++) {
            this.ruffini(divisors[i]);
            if (this.nums[this.nums.length-1] == 0) {
                sol[i] = divisors[i];
                llevazeros();
                if (this.nums.length - 1 == 2) {
                    float [] aux = Arrel(this);
                    sol[2] = aux[0];
                    sol[3] = aux[1];
                    Arrays.sort(sol);
                    return sol;
                }
            }
        }
        return null;
    }

    private float [] biquaD() {
        float[] sol = this.nums;
        float[] aux = {sol[0], sol[2], sol[4]};
        Polynomial bi = new Polynomial(aux);
        if (solPos(aux) > 0) {
            sol = new float[4];
            aux = Arrel(bi);
            double pos0 = Math.sqrt(aux[0]);
            double pos1 = Math.sqrt(aux[1]);
            sol[0] = (float) pos1 * -1;
            sol[1] = (float) pos0 * -1;
            sol[2] = (float) pos0;
            sol[3] = (float) pos1;
            return sol;
        } else if (solPos(aux) == 0) {
            sol = new float[2];
            aux = Arrel(bi);
            double pos = Math.sqrt(aux[0]);
            sol[0] = (float) pos * -1;
            sol[1] = (float) pos;
            return sol;
        } else {
            return null;
        }
    }

    private int solPos(float[] nums) {
        int solPos = (int) ((nums[1] * nums[1]) - (4 * nums[0] * nums[2]));
        return solPos;
    }

    private float[] soloX(Polynomial p) {
        if (this.nums[0] > 1) {
            float[] sol = {p.nums[0] / (p.nums[1] * -1)};
            return sol;
        } else {
            float[] sol = {p.nums[1] * -1};
            return sol;
        }
    }

    private float[] arrelmasdosprimajor1(double grade, double solpos) {
        if (grade % 2 == 0) {
            double sol = Math.pow(solpos*-1, 1.0 / grade);
            if (sol > 0) {
                float[] retorn = new float[2];
                retorn[0] = (float) sol * -1;
                retorn[1] = (float) sol;
                return retorn;
            } else {
                return null;
            }
        } else {
            double sol = Math.pow(solpos, 1.0/grade);
            float[] retorn = {(float) sol*-1};
            return retorn;
        }
    }

    private float[] Arrelmasdos(float[] nums) {
        double grade = nums.length - 1;
        double solpos = nums[nums.length - 1];
        if (nums[0] == 1) {
            float[] sol = arrelmasdosprimajor1(grade, solpos);
            return sol;
        } else if (nums[0] != 1) {
            solpos = nums[0] / solpos * -1;
            float[] sol = arrelmasdosprimajor1(grade, solpos);
            return sol;
        } else {
            return null;
        }
    }

    private float[] Arrel(Polynomial p) {
        double part2 = solPos(p.nums);
        if (part2 < 0) {
            return null;
        } else if (part2 == 0) {
            float[] result = {-p.nums[1] / (p.nums[0] * 2)};
            return result;
        } else {
            part2 = Math.sqrt(part2);
            float[] result = new float[2];
            result[1] = (-p.nums[1] + (float) part2) / (p.nums[0] * 2);
            result[0] = (-p.nums[1] - (float) part2) / (p.nums[0] * 2);
            return result;
        }
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        return this.toString().equals(p.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        if (this.nums[0] == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] == 0) {
                continue;
            }
            if (this.nums.length == 1) {
                return String.valueOf(this.nums[i]);
            } else if (this.nums.length == 2) {
                if (this.nums[i] == 1 && i == 0) {
                    sb.append("x");
                } else if (this.nums[i] == -1 && i == 0) {
                    sb.append("-x");
                } else if (this.nums[i] != 1 && i == 0) {
                    sb.append((int) this.nums[i] + "x");
                } else if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] > 0 && i > 0) {
                    sb.append(" + " + (int) this.nums[i]);
                } else if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] < 0 && i > 0) {
                    sb.append(" - " + (int) this.nums[i] * -1);
                }
            } else if (this.nums.length > 2) {
                if (i == 0) {
                    if (this.nums[i] == 1) {
                        sb.append("x^" + (this.nums.length - 1));
                    } else if (this.nums[i] == -1) {
                        sb.append("-x^" + (this.nums.length - 1));
                    } else if (this.nums[i] < 1 || this.nums[i] > 1) {
                        sb.append((int) this.nums[i] + "x^" + (this.nums.length - 1));
                    }
                } else if (i > 0) {
                    if (this.nums.length - i - 1 > 1) {
                        if (this.nums[i] == 1) {
                            sb.append(" + x^" + (this.nums.length - 1 - i));
                        } else if (this.nums[i] == -1) {
                            sb.append(" - x^" + (this.nums.length - 1 - i));
                        } else if (this.nums[i] > 1) {
                            sb.append((" + " + (int) this.nums[i] + "x^" + (this.nums.length - 1 - i)));
                        } else if (this.nums[i] < 1) {
                            sb.append((" - " + (int) this.nums[i] * -1 + "x^" + (this.nums.length - 1 - i)));
                        }
                    } else if (this.nums.length - i - 1 == 1) {
                        if (this.nums[i] == 1) {
                            sb.append(" + x");
                        } else if (this.nums[i] == -1) {
                            sb.append(" - x");
                        } else if (this.nums[i] > 1) {
                            sb.append(" + " + (int) this.nums[i] + "x");
                        } else if (this.nums[i] < 1) {
                            sb.append(" - " + (int) this.nums[i] * -1 + "x");
                        }
                    } else if (this.nums.length - i - 1 == 0) {
                        if (this.nums[i] == 1) {
                            sb.append(" + 1");
                        } else if (this.nums[i] == -1) {
                            sb.append(" - 1");
                        } else if (this.nums[i] > 1) {
                            sb.append(" + " + (int) this.nums[i]);
                        } else if (this.nums[i] < 1) {
                            sb.append(" - " + (int) this.nums[i] * -1);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}