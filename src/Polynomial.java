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
        //Afegim al array defloats que ens passen al array del propi polinomi
        this.nums = new float[cfs.length];
        for (int i = 0; i < cfs.length; i++) {
            //Amb aquest constructor no deixarem que tengui cap cero a la part esquerra, sempre tendrà que començar per
            //un numero diferent a zero
            if (cfs[0] == 0) {
                for (int j = 0; j < cfs.length; j++) {
                    //Quan trobam on hi ha un numero diferent a zero realment es quan cream el array be i despres li
                    //ficam tots els valors que conté
                    if (cfs[j] != 0) {
                        //Crearem un array de la longitud original menys j ja que j serà allà on es troba el nombre
                        //Diferent a zero
                        this.nums = new float[cfs.length - j];
                        for (int k = 0; k < cfs.length - j; k++) {
                            this.nums[k] = cfs[k + j];
                        }
                        break;
                    }
                }
                //En el cas que no contengui ceros al principi simplement el copia tal cual
            } else {
                this.nums[i] = cfs[i];
            }
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        //Guardam dins una variable 1 o -1 per saber el signe que tendrà un monomi
        float signe = 1;
        //Separam el string que ens passen per els espais, i tenim monòmi a monomi
        String[] original = s.split(" ");
        for (String ss : original) {
            //Modificam el signe depenguent del signe que tengui el monomi
            if (ss.equals("+")) {
                signe = 1;
                continue;
            }
            if (ss.equals("-")) {
                signe = -1;
                continue;
            }
            //treim el exponent (explicat dins la funció)
            int exponent = treuExp(ss);
            //treim el coeficient (explicat dins la funció)
            float coeficient = treuCoe(ss) * signe;
            //si el cocient es 0 que passi a la següent iteració ja que no ens interesa
            if (coeficient == 0) {
                continue;
            }
            //col·locam el cocient en la posició que li correspon
            setCoef(coeficient, exponent);
        }
        //giram l'array per tenirlo ordenat de major exponent a menor
        flipIt(this.nums);
    }

    //Funció que simplement ens gira un array
    private void flipIt(float[] p) {
        for (int i = 0; i < p.length / 2; i++) {
            float aux = p[i];
            p[i] = p[p.length - i - 1];
            p[p.length - i - 1] = aux;
        }
    }

    //Ens col·loca el cocient en el lloc que li correspon
    private void setCoef(float coef, int expo) {
        //Primer de tot comprovam que l'array que tenim actualment sigui major que l'exponent que té el numero
        if (expo >= this.nums.length) {
            //si no és aquest cas cream un altre array que tengui la capcitat suficient
            float[] aux = new float[expo + 1];
            //col·locam el cocient all lloc corresponent
            aux[expo] += coef;
            //anam copiant l'array actual per el nou
            for (int i = 0; i < aux.length; i++) {
                if (i <= this.nums.length - 1) {
                    aux[i] += this.nums[i];
                }
            }
            //feim que l'array actual sigui el nou que hem creat
            this.nums = aux;
        } else {
            //en cas contrari simplement sumam el cocient al lloc del exponent per si tenim dos cocients que tenguin el
            //mateix exponent
            this.nums[expo] += coef;
        }
    }

    //Ens treu el exponent
    private int treuExp(String s) {
        //declaram el valor de retorn que per defecte serà 0
        int exp = 0;
        //si tenim una x unicament sabem perfectament que el exponent serà 1
        if (s.contains("x") && !s.contains("^")) {
            exp = 1;
            return exp;
        } else {
            //però en cas contrari hem de saber el que tenim despres del '^' (elevat)
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '^') {
                    exp = Integer.parseInt(s.substring(i + 1));
                }
            }
        }
        //retornam el exponent
        return exp;
    }

    //Ens treu el coeficient
    private float treuCoe(String s) {
        //declaram el valor de retorn que per defecte serà 1
        float coe = 1;
        //si és un numero totsol simplement el retornam tal cual és amb la funció de java parseFloat
        if (!s.contains("x") && !s.contains("^")) {
            coe = Float.parseFloat(s);
            return coe;
        } else {
            //en cas contrari hem de saber quin número és i ho feim amb el index desde on es troba el numero fins a la
            //x
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'x' && i > 0) {
                    if (i > 0 && s.charAt(i - 1) == '-') {
                        return -1;
                    }
                    coe = Float.parseFloat(s.substring(0, i));
                }
            }
        }
        //i finalment el retornam
        return coe;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        //
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
            float actual = this.nums[i];
            if (actual == 0) {
                continue;
            }
            res[i] += num * actual;
        }
        return new Polynomial(res);
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
        float [] sol;
        if (this.nums.length - 1 == 1) {
            sol = soloX(this);
            return sol;
        } else if (this.nums.length - 1 >= 2) {
            if (this.nums.length - 1 == 2) {
                sol = Arrel(this);
                return sol;
            } else if (this.nums.length - 1 > 2) {
                if (this.nums.length - 1 == 4 && this.nums[1] == 0 && this.nums[3] == 0 && this.nums[2] != 0) {
                    sol = biquaD();
                    return sol;
                } else if (this.nums[1] == 0) {
                    sol = Arrelmasdos(this.nums);
                    return sol;
                } else {
                    float[] divisors = getDivisors(this.nums[this.nums.length - 1]);
                    sol = diferent(divisors);
                    return sol;
                }
            }
        }
        return null;
    }

    private float[] getDivisors(float num) {
        int cont = 0;
        for (int i = 0; i < num; i++) {
            if (num % i == 0) {
                cont++;
            }
        }
        float[] divisors = new float[cont];
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
                float[] be = new float[i];
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
            if (i < this.nums.length - 1) {
                this.nums[i + 1] += this.nums[i];
            } else {
                break;
            }
        }
    }

    private float[] diferent(float[] divisors) {
        float[] sol = new float[4];
        for (int i = 0; i < divisors.length; i++) {
            this.ruffini(divisors[i]);
            if (this.nums[this.nums.length - 1] == 0) {
                sol[i] = divisors[i];
                llevazeros();
                if (this.nums.length - 1 == 2) {
                    float[] aux = Arrel(this);
                    sol[2] = aux[0];
                    sol[3] = aux[1];
                    Arrays.sort(sol);
                    return sol;
                }
            }
        }
        return null;
    }

    private float[] biquaD() {
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
       return (int) ((nums[1] * nums[1]) - (4 * nums[0] * nums[2]));
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
            double sol = Math.pow(solpos * -1, 1.0 / grade);
            if (sol > 0) {
                float[] retorn = new float[2];
                retorn[0] = (float) sol * -1;
                retorn[1] = (float) sol;
                return retorn;
            } else {
                return null;
            }
        } else {
            double sol = Math.pow(solpos, 1.0 / grade);
            float[] retorn = {(float) sol * -1};
            return retorn;
        }
    }

    private float[] Arrelmasdos(float[] nums) {
        float [] sol;
        double grade = nums.length - 1;
        double solpos = nums[nums.length - 1];
        if (nums[0] == 1) {
            sol = arrelmasdosprimajor1(grade, solpos);
            return sol;
        } else if (nums[0] != 1) {
            solpos = nums[0] / solpos * -1;
            sol = arrelmasdosprimajor1(grade, solpos);
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