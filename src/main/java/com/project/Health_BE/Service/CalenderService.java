package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.CalenderDto;
import com.project.Health_BE.Dto.CalenderGet;
import com.project.Health_BE.Dto.CalenderResponseDto;
import com.project.Health_BE.Entity.ExerciseLog;
import com.project.Health_BE.Entity.ExerciseSetLog;
import com.project.Health_BE.Exception.UserNotFoundException;
import com.project.Health_BE.Repository.ExerciseLogReposiroty;
import com.project.Health_BE.Repository.ExerciseRepository;
import com.project.Health_BE.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CalenderService {
    private final ExerciseLogReposiroty exerciseLogReposiroty;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public CalenderService(ExerciseLogReposiroty ex, UserRepository ur, ExerciseRepository er) {
        this.exerciseLogReposiroty = ex;
        this.userRepository = ur;
        this.exerciseRepository = er;
    }

    public Long post(CalenderDto dto) {
        try {
            if(userRepository.findById(dto.getUserId()).isEmpty())
                throw new UserNotFoundException("존재하지 않는 userId");
            if(exerciseRepository.findbyname(dto.getExercise()).isEmpty())
                throw new UserNotFoundException("존재하지 않는 Exercise");
            ExerciseLog Entity = new ExerciseLog()
                    .builder()
                    .user(userRepository.findById(dto.getUserId()).get())
                    .exercise(exerciseRepository.findbyname(dto.getExercise()).get())
                    .date(dto.getDate())
                    .extime(dto.getTime())
                    .memo(dto.getMemo())
                    .fatigueLevel(dto.getFatigue())
                    .build();
            for(int i = 1; i <= dto.getSet(); i++) {
                ExerciseSetLog log = ExerciseSetLog
                        .builder()
                        .exerciseLog(Entity)
                        .setNumber(i)
                        .weightKg(BigDecimal.valueOf(dto.getWeight()))
                        .build();
                Entity.addExerciseSetLog(log);
            }
            exerciseLogReposiroty.save(Entity);
            return dto.getUserId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CalenderResponseDto get(CalenderGet request) {
        try {
            if(exerciseLogReposiroty.findbydate(request.getDate()).isEmpty())
                throw new UserNotFoundException(request.getDate().toString()+"의 일정은 저장되어 있지 않습니다.");
            ExerciseLog entity = exerciseLogReposiroty.findbydate(request.getDate()).get();
            int grass = entity.getExtime() / 100;
            if(grass > 100) grass = 100;
            return CalenderResponseDto
                    .builder()
                    .exercise(entity.getExercise().getName())
                    .userId(entity.getUser().getUserId())
                    .date(entity.getDate())
                    .weight(entity.getUser().getWeight())
                    .time(entity.getExtime())
                    .set(entity.getExerciseSetLogs().size())
                    .fatigue(entity.getFatigueLevel())
                    .memo(entity.getMemo())
                    .grass(grass)
                    .build();
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
