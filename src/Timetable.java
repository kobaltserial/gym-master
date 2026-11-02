import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, List<TrainingSession>> dailySchedule =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>());
        List<TrainingSession> sessions = dailySchedule.computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>());
        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);
        if (schedule == null) {
            return List.of();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : schedule.values()) {
            result.addAll(sessions);
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);
        if (schedule == null) {
            return List.of();
        }
        List<TrainingSession> trainingSessions = schedule.get(timeOfDay);
        if (trainingSessions == null) {
            return List.of();
        }
        return trainingSessions;
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        HashMap<Coach, Integer> coachCounts = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> schedule : timetable.values()) {
            for (List<TrainingSession> trainingSessions : schedule.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> entries = new ArrayList<>(coachCounts.entrySet());
        entries.sort((entry1, entry2) -> {
            return Integer.compare(entry2.getValue(), entry1.getValue());
        });

        return entries;
    }
}
