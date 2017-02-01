

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
        return false;
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums.length == 1) {
                if (this.nums[i] == 0 )  {
                    sb.append(0);
                    continue;
                }
            }
            if (this.nums.length > 1) {
                if (this.nums[i] == 0 && i == 0) {
                    sb.append(0);
                    continue;
                }
                if (this.nums[i] == 1) {
                    sb.setCharAt(i,'x');
                }
                if (this.nums[i] != 1 && this.nums[i] != 0) {
                    sb.append((int) this.nums[i] + "x^" + this.nums.length+1 );
                }
            }
        }
        return sb.toString();
    }
}
