// class TestWave {

//     public static void main(String[] args) {

//         // Start with a flat wave (all zeros)
//         WaveSimulation.resetWave();

//         // Choose the middle of the wave
//         int mid = WaveSimulation.SIZE / 2;

//         // Disturb the middle bar by pulling it up
//         WaveSimulation.disturb(mid, 5.0);

//         // Print a few steps so we can see how the wave moves
//         for (int step = 0; step < 20; step++) {

//             System.out.println("Step " + step + ":");

//             // Print only a small section around the middle
//             for (int i = mid - 5; i <= mid + 5; i++) {
//                 System.out.print(String.format("%.3f ", WaveSimulation.getHeight(i)));
//             }

//             System.out.println("\n");
            
//             // Advance the simulation one step
//             WaveSimulation.stepWave();
//         }
//     }
// }

