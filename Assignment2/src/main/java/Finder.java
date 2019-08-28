public class Finder {
    public Integer findMax(int[] intArray){
        if(intArray == null || intArray.length == 0){
            return null;
        }
        int max = Integer.MIN_VALUE;
        for(int num : intArray){
            if(num > max){
                max = num;
            }
        }
        return max;
    }

    public Integer findMin(int[] intArray){
        if(intArray == null || intArray.length == 0){
            return null;
        }
        int min = Integer.MAX_VALUE;
        for(int num : intArray){
            if(num < min){
                min = num;
            }
        }
        return min;
    }

}
