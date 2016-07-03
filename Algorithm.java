package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by nphl on 01.07.2016.
 */
public class Algorithm {

    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    private void fill(int[] toFill, int value) {
        for (int i = 0; i < toFill.length; i++) toFill[i] = value;
    }

    private void fill2(int[][] tof, int size, int value) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) tof[i][j] = value;
        }
    }

    public int INF = Integer.MAX_VALUE / 2; // "Бесконечность"
    public int mstPrim(int[][] graph, int vNum, List<GraphStruct> toDraw) {

        boolean[] used = new boolean[vNum]; // массив пометок
        int[][] ddist = new int[vNum][3];
        for (int i = 0; i < vNum; i++) ddist[i][0] = 0;
        int[] dist = new int[vNum]; // массив расстояния. dist[v] = вес_ребра(MST, v)
        fill(dist, INF); // устанаавливаем расстояние до всех вершин INF
        dist[0] = 0; // для начальной вершины положим 0
        ddist[0][0] = 0;
        for (; ; ) {
            int v = -1;
            for (int nv = 0; nv < vNum; nv++) // перебираем вершины
                if (!used[nv] && dist[nv] < INF && (v == -1 || dist[v] > dist[nv]))
                    v = nv;// выбираем самую близкую непомеченную вершину

            if (v == -1) break; // ближайшая вершина не найдена
            used[v] = true; // помечаем ее
            for (int nv = 0; nv < vNum; nv++) {
                if (!used[nv] && graph[v][nv] < INF) { // для всех непомеченных смежных
                    dist[nv] = min(dist[nv], graph[v][nv]); // улучшаем оценку расстояния (релаксация)
                    if (graph[v][nv] == dist[nv]) {
                        ddist[nv][0] = dist[nv];
                        ddist[nv][1] = v;
                        ddist[nv][2] = nv;
                    }
                }
            }

        }

        for (int i = 1; i < vNum; i++) {
            if (ddist[i][0] == 0) break;
            toDraw.add(new GraphStruct(ddist[i][1]+1,ddist[i][2]+1,ddist[i][0]));
        }
        int ret = 0; // вес MST
        for (int v = 0; v < vNum; v++)
            ret += dist[v];
        return ret;
    }
}
